package string;

/**
 * @author mti1301
 * @since 2015/8/2.
 */
public class StringUseCase {

    public static void main(String[] args) {
//        useEqualSign();
        useEqualsMethod();
    }

    public static void useEqualSign() {
        //Test1;
        String aStr = "Hello World!";
        String bStr = "Hello World!";
        assert aStr == bStr;

        //Test2:编译器优化，将Hello+World合并成HelloWorld
        //因为“+”号左右都是常量，编译器认为不需要计算了
        String cStr = "Hello" + " World!";
        String dStr = "Hello World!";
        assert cStr == dStr;
    }

    public static void useEqualsMethod() {
        //Test1
        String aStr = "Hello World!";
        String bStr = "Hello World!";
        assert aStr.equals(bStr);

        //Test2:编译器优化，将Hello+World合并成HelloWorld
        String cStr = "Hello" + " World!";
        String dStr = "Hello World!";
        assert cStr.equals(dStr);
    }
}


