import parser.Parser;
import scanner.MyScanner;
import scanner.OberonScanner;

/**
 * 编译器的入口
 */
public class ParserMain {
    /**
     * 编译器的入口
     * @param argv 需要编译的文件路径
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
            System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
        } catch (Exception e) {
            System.out.println("Error at (" + parser.getLine() + "," + parser.getColumn() + ")");
            System.out.println("Error message:");
            e.printStackTrace();
        }
    }
}
