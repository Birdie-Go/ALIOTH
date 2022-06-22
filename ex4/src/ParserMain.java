import parser.Parser;
import scanner.MyScanner;
import scanner.OberonScanner;

/**
 * �����������
 */
public class ParserMain {
    /**
     * �����������
     * @param argv ��Ҫ������ļ�·��
     * @throws Exception exception happens in parser
     */
    public static void main(String[] argv) throws Exception {
        OberonScanner scanner = new OberonScanner(new java.io.FileReader(argv[0]));
        MyScanner myScanner = new MyScanner(scanner);
        Parser parser = new Parser(myScanner);
        try {
            long startTime = System.currentTimeMillis();
            int result = parser.parse();
            long endTime = System.currentTimeMillis();
            System.out.println(result);
            System.out.println("��������ʱ�䣺 " + (endTime - startTime) + "ms");
        } catch (Exception e) {
            System.out.println("Error at (" + parser.getLine() + "," + parser.getColumn() + ")");
            System.out.println("Error message:");
            e.printStackTrace();
        }
    }
}
