package table.type;

/**
 * ���ͻ���
 */
public abstract class Types {
    /** �������� */
    protected String name;

    /**
     * ��ȡ����
     * @return ��������
     */
    public String getType() {
        return name;
    }

    /**
     * �ж����������Ƿ����
     * @param a ����a
     * @param b ����b
     * @return �Ƿ����
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
