package table.type;

/**
 * 类型基类
 */
public abstract class Types {
    /** 类型名称 */
    protected String name;

    /**
     * 获取类型
     * @return 类型名称
     */
    public String getType() {
        return name;
    }

    /**
     * 判断两个类型是否兼容
     * @param a 类型a
     * @param b 类型b
     * @return 是否兼容
     */
    static public boolean equal(Types a, Types b) {
        if (a.getType().equals("type"))
            return Types.equal(((TypeType)a).getTypeType(), b);
        if (b.getType().equals("type"))
            return Types.equal(a, ((TypeType)b).getTypeType());
        if (a.getType().equals("array"))
            return Types.equal(((ArrayType)a).getArrayType(), b);
        if (b.getType().equals("array"))
            return Types.equal(a, ((ArrayType)b).getArrayType());

        if (!a.getType().equals(b.getType()))
            return false;
        switch (a.getType()) {
            case "int":
            case "bool":
                return true;
            case "record":
                return RecordType.ifEqual((RecordType)a, (RecordType)b);
        }
        return true;
    }
}
