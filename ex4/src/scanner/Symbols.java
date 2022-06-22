package scanner;

/**
 * token��
 */
public class Symbols {
    /** token����� */
    private String token;
    /** tokenֵ */
    private String value;
    /** �����к� */
    private int line;
    /** �����к� */
    private int column;

    /**
     * ���캯��
     * @param _token token���
     * @param _line �к�
     * @param _column �к�
     */
    public Symbols(String _token, int _line, int _column) {
        token = _token;
        line = _line;
        column = _column;
    }

    /**
     * ��ֵ�ͱ������캯��
     * @param _token token���
     * @param _line �к�
     * @param _column �к�
     * @param _value token����
     */
    public Symbols(String _token, int _line, int _column, String _value) {
        token = _token;
        line = _line;
        column = _column;
        value = _value;
    }

    /**
     * ��ȡtoken���
     * @return String, ���
     */
    public String getToken() {
        return token;
    }

    /**
     * ��ȡtoken�к�
     * @return int, �к�
     */
    public int getLine() {
        return line;
    }

    /**
     * ��ȡtoken�к�
     * @return int, �к�
     */
    public int getColumn() {
        return column;
    }

    /**
     * ��ȡtokenֵ
     * @return String, ֵ
     */
    public String getValue() {
        return value;
    }
}
