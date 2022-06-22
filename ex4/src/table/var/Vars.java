package table.var;
import table.type.*;

/**
 * 变量类, 表示一个变量
 */
public class Vars {
    /** 变量名 */
    private String name;
    /** 变量类型 */
    private Types type;
    /** 是否是常量 */
    private boolean isConst;

    /**
     * 普通构造函数
     * @param _name 变量名
     * @param _type 类型
     * @param _const 是否是常量
     */
    public Vars(String _name, Types _type, boolean _const) {
        name = _name;
        type = _type;
        isConst = _const;
    }

    /**
     * 复制变量构造函数
     * @param _name 变量名
     * @param _copy 复制的变量
     * @param _const 是否是常量
     */
    public Vars(String _name, Vars _copy, boolean _const) {
        name = _name;
        type = _copy.type;
        isConst = _const;
    }
    
    /**
     * 获取变量名
     * @return 变量名
     */
    public String getName() {
        return name;
    }

    /**
     * 获取变量类型
     * @return 变量类型
     */
    public Types getType() {
        while (type.getType().equals("type")) {
            type = ((TypeType)type).getTypeType();
        }
        return type;
    }

    /**
     * 是否是常量
     * @return 是否是常量
     */
    public boolean isItConst() {
        return isConst;
    }

    /**
     * 判断变量类型是否相同
     * @param a 变量a
     * @param b 变量b
     * @return 类型hi否相同
     */
    public static boolean sameType(Vars a, Vars b) {
        return Types.equal(a.type, b.type);
    }
}
