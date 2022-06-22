import complexity.Calculator;
import complexity.ComplexityTester;
import java.io.*;

/**
 * �����������
 */
public class TestMain implements Calculator {
    /**
     * �����������
     * @param args none
     * @throws Exception exception happens in tester
     */
    public static void main(String[] args) throws Exception {
        ComplexityTester tester= new ComplexityTester(new TestMain());
        tester.simpleTest(); //�򵥲���
        tester.fullTest(); //ʹ��������������
        double rate = tester.getRate(); //��ȡ���Գɹ���
        System.out.print("Success rate : ");
        System.out.println(rate);
    }

    @Override
    public String calculate(String arg0) {
        try {
            StringReader reader = new StringReader(arg0);
            OberonScanner scanner = new OberonScanner(reader);
            MyScanner myScanner = new MyScanner(scanner);
            Parser parser = new Parser(myScanner);
            try {
                // String answer = String.valueOf(parser.parse().sym);
                int answer = (int)parser.parse().value;
                return String.valueOf(answer);
            } catch (Exception e) {
                System.out.println("Error at (" + parser.getLine() + "," + parser.getColumn() + ")");
                System.out.println("Error message:");
                e.printStackTrace();
            }
            return "0";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }
}