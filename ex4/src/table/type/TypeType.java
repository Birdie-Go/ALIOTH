package table.type;

/**
 * type类型
 */
public class TypeType extends Types {
    /** type名称 */
    private String typeName;
    /** type类型 */
    private Types type;

    /**
     * 构造函数
     * @param _typeName type变量名
     * @param _type type实际类型
     */
    public TypeType(String _typeName, Types _type) {
        name = "type";
        typeName = _typeName;
        type = _type;
    }

    /**
     * 获取type变量名
     * @return type变量名
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 获取type实际类型
     * @return type实际类型
     */
    public Types getTypeType() {
        return type;
    }

    /**
     * 判断两个type类型是否一致
     * @param a type a
     * @param b type b
     * @return 是否一致
     */
    static public boolean ifEqual(TypeType a, TypeType b) {
        return Types.equal(a.type, b.type);
    }
}
