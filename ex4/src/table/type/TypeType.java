package table.type;

/**
 * type����
 */
public class TypeType extends Types {
    /** type���� */
    private String typeName;
    /** type���� */
    private Types type;

    /**
     * ���캯��
     * @param _typeName type������
     * @param _type typeʵ������
     */
    public TypeType(String _typeName, Types _type) {
        name = "type";
        typeName = _typeName;
        type = _type;
    }

    /**
     * ��ȡtype������
     * @return type������
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * ��ȡtypeʵ������
     * @return typeʵ������
     */
    public Types getTypeType() {
        return type;
    }

    /**
     * �ж�����type�����Ƿ�һ��
     * @param a type a
     * @param b type b
     * @return �Ƿ�һ��
     */
    static public boolean ifEqual(TypeType a, TypeType b) {
        return Types.equal(a.type, b.type);
    }
}
