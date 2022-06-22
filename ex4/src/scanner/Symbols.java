package scanner;

/**
 * token类
 */
public class Symbols {
    /** token的类别 */
    private String token;
    /** token值 */
    private String value;
    /** 所在行号 */
    private int line;
    /** 所在列号 */
    private int column;

    /**
     * 构造函数
     * @param _token token类别
     * @param _line 行号
     * @param _column 列号
     */
    public Symbols(String _token, int _line, int _column) {
        token = _token;
        line = _line;
        column = _column;
    }

    /**
     * 数值和变量构造函数
     * @param _token token类别
     * @param _line 行号
     * @param _column 列号
     * @param _value token内容
     */
    public Symbols(String _token, int _line, int _column, String _value) {
        token = _token;
        line = _line;
        column = _column;
        value = _value;
    }

    /**
     * 获取token类别
     * @return String, 类别
     */
    public String getToken() {
        return token;
    }

    /**
     * 获取token行号
     * @return int, 行号
     */
    public int getLine() {
        return line;
    }

    /**
     * 获取token列号
     * @return int, 列号
     */
    public int getColumn() {
        return column;
    }

    /**
     * 获取token值
     * @return String, 值
     */
    public String getValue() {
        return value;
    }
}
