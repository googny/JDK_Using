package net.inetaddress;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mti1301 on 2015/5/26.
 */
public class PooledWebLog {
    private BufferedReader reader;
    private BufferedWriter writer;
    private int numOfThreads;
    private List entries = Collections.synchronizedList(new LinkedList());
    private boolean isFinished;

    public PooledWebLog(InputStream in, OutputStream out, int numOfThreads) {
        reader = new BufferedReader(new InputStreamReader(in));
        writer = new BufferedWriter(new OutputStreamWriter(out));
        this.numOfThreads = numOfThreads;
        isFinished = false;
    }

    public boolean isFinished() {
        return this.isFinished;
    }

    public int getNumOfThreads() {
        return this.numOfThreads;
    }

    public void processLogFile() {
        for (int i = 0; i < numOfThreads; i++) {
            // 开启工作线程
            new LookupThread(entries,this).start();
        }

        try {
            String entry = reader.readLine();
            while (entry != null) {
                if (entries.size() > numOfThreads) {
                    try {
                        Thread.sleep(1000 / numOfThreads);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                synchronized (entries) {
                    entries.add(0, entry);
                    // 通知阻塞等待entries元素的线程运行
                    entries.notifyAll();
                }

                entry = reader.readLine();
                // 让出CPU时间片
                Thread.yield();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.isFinished = true;
        // 通知等待的线程结束
        synchronized (entries){
            entries.notifyAll();
        }
    }

    public void log(String entry) throws IOException {
        writer.write(entry+System.getProperty("line.separator","\r\n"));
        writer.flush();
    }

    public static void main(String[] args) {

        try {
            PooledWebLog pwl = new PooledWebLog(new FileInputStream("JDKUsing/Web.log"),System.out,100);
            pwl.processLogFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class LookupThread extends Thread{
    private List entries;
    PooledWebLog log;
    public LookupThread(List entries,PooledWebLog log){
        this.entries = entries;
        this.log = log;
    }

    public void run(){
        String entry = null;
        while (true){
            synchronized (entries){
                while (entries.size() == 0){
                    if (log.isFinished()) return;
                    try {
                        entries.wait();
                    } catch (InterruptedException e) {
                    }
                }
                entry = (String) entries.remove(entries.size() -1);
            }
            int index = entry.indexOf(' ');
            String ip = entry.substring(0,index);
            String theRest = entry.substring(index,entry.length());
            try {
                InetAddress address = InetAddress.getByName(ip);
                log.log(address.getHostName()+theRest);
            } catch (UnknownHostException e) {
                try {
                    log.log(entry);
                } catch (IOException e1) {
                }
            } catch (IOException e) {
            }
            this.yield();
        }
    }
}