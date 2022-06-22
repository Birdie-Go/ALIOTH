import java.io.*;
import exceptions.*;

/**
 * �ʷ������������
 */
public class LexicalMain {
    /**
     * �ʷ������������
     * @param args oberon-0����
     * @throws Exception �ʷ�����
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
                    System.out.print("\n" + scanner.yytext() + " ��������: ");
                    System.out.println(e);
                    System.out.println("\n");
                    flag = false;
                    // break; // ��ʵ���������Կ������еĴʷ����󣬵��Ǵ�ӡ��ͦ�ѿ���
                }
            }
            if (flag)
                System.out.println("�޴ʷ�����!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
