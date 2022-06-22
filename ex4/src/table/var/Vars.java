package table.var;
import table.type.*;

/**
 * ������, ��ʾһ������
 */
public class Vars {
    /** ������ */
    private String name;
    /** �������� */
    private Types type;
    /** �Ƿ��ǳ��� */
    private boolean isConst;

    /**
     * ��ͨ���캯��
     * @param _name ������
     * @param _type ����
     * @param _const �Ƿ��ǳ���
     */
    public Vars(String _name, Types _type, boolean _const) {
        name = _name;
        type = _type;
        isConst = _const;
    }

    /**
     * ���Ʊ������캯��
     * @param _name ������
     * @param _copy ���Ƶı���
     * @param _const �Ƿ��ǳ���
     */
    public Vars(String _name, Vars _copy, boolean _const) {
        name = _name;
        type = _copy.type;
        isConst = _const;
    }
    
    /**
     * ��ȡ������
     * @return ������
     */
    public String getName() {
        return name;
    }

    /**
     * ��ȡ��������
     * @return ��������
     */
    public Types getType() {
        while (type.getType().equals("type")) {
            type = ((TypeType)type).getTypeType();
        }
        return type;
    }

    /**
     * �Ƿ��ǳ���
     * @return �Ƿ��ǳ���
     */
    public boolean isItConst() {
        return isConst;
    }

    /**
     * �жϱ��������Ƿ���ͬ
     * @param a ����a
     * @param b ����b
     * @return ����hi����ͬ
     */
    public static boolean sameType(Vars a, Vars b) {
        return Types.equal(a.type, b.type);
    }
}
