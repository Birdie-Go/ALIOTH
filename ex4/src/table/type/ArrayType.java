package table.type;

/**
 * 数组类型
 */
public class ArrayType extends Types {
    /** 数组范围 */
    private int range;
    /** 数组类型 */
    private Types type;

    /**
     * 构造函数
     * @param _range 数组范围
     * @param _type 数组类型
     */
    public ArrayType(int _range, Types _type) {
        name = "array";
        range = _range;
        type = _type;
    }

    /**
     * 获取数组的范围
     * @return 范围
     */
    public int getRange() {
        return range;
    }

    /**
     * 获取数组的类型
     * @return 数组的类型
     */
    public Types getArrayType() {
        return type;
    }

    /**
     * 判断两个数组类型是否兼容
     * @param a 数组a
     * @param b 数组b
     * @return 是否类型兼容
     */
    static public boolean ifEqual(ArrayType a, ArrayType b) {
        if (a.range != b.range)
            return false;
        return Types.equal(a.type, b.type);
    }
}
