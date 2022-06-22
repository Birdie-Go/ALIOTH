import java.io.*;
import exceptions.*;

/**
 * 词法分析器的入口
 */
public class LexicalMain {
    /**
     * 词法分析器的入口
     * @param args oberon-0代码
     * @throws Exception 词法错误
     */
    public static void main(String[] args) throws Exception {
        OberonScanner scanner = new OberonScanner(new java.io.FileReader(args[0]));
        StringBuilder builder = new StringBuilder();
        try {
            boolean flag = true;
            while (!scanner.yyatEOF()) {
                try {
                    String workClass = scanner.yylex();
                    System.out.println(scanner.yytext() + " : " + workClass);
                } catch (LexicalException e) {
                    System.out.print("\n" + scanner.yytext() + " 发生错误: ");
                    System.out.println(e);
                    System.out.println("\n");
                    flag = false;
                    // break; // 其实不加这句可以看到所有的词法错误，但是打印会挺难看的
                }
            }
            if (flag)
                System.out.println("无词法错误!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
