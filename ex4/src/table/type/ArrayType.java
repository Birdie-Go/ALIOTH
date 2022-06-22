package table.type;

/**
 * ��������
 */
public class ArrayType extends Types {
    /** ���鷶Χ */
    private int range;
    /** �������� */
    private Types type;

    /**
     * ���캯��
     * @param _range ���鷶Χ
     * @param _type ��������
     */
    public ArrayType(int _range, Types _type) {
        name = "array";
        range = _range;
        type = _type;
    }

    /**
     * ��ȡ����ķ�Χ
     * @return ��Χ
     */
    public int getRange() {
        return range;
    }

    /**
     * ��ȡ���������
     * @return ���������
     */
    public Types getArrayType() {
        return type;
    }

    /**
     * �ж��������������Ƿ����
     * @param a ����a
     * @param b ����b
     * @return �Ƿ����ͼ���
     */
    static public boolean ifEqual(ArrayType a, ArrayType b) {
        if (a.range != b.range)
            return false;
        return Types.equal(a.type, b.type);
    }
}
