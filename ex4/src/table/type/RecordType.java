package table.type;
import java.util.*;
import table.var.*;

/**
 * Record����
 */
public class RecordType extends Types {
    /** ������ */
    private ArrayList <Vars> son;
    /** ���캯�� */
    public RecordType() {
        name = "record";
        son = new ArrayList<>();
    }

    /**
     * ��ȡ���еĲ���
     * @return �����б�
     */
    public ArrayList <Vars> getSon() {
        return son;
    }

    /**
     * ����һ������
     * @param aNew ����
     */
    public void add(Vars aNew) {
        son.add(aNew);
    }

    /**
     * �������Ʋ���ĳһ������
     * @param id ������
     * @return ����
     */
    public Vars findSon(String id) {
        for (int i = 0; i < son.size(); i++)
            if (son.get(i).getName().equals(id))
                return son.get(i);
        return null;
    }

    /**
     * �ж������ṹ���Ƿ�ͬһ����
     * @param a �ṹ��a
     * @param b �ṹ��b
     * @return �Ƿ�ͬһ����
     */
    static public boolean ifEqual(RecordType a, RecordType b) {
        return a == b;
        // if (a.son.size() != b.son.size())
        //     return false;
        // boolean flag = true;
        // for (int i = 0; i < a.son.size(); i++) {
        //     flag &= Types.equal(a.son.get(i).getType(), b.son.get(i).getType());
        //     flag &= (a.son.get(i).getName() == b.son.get(i).getName());
        // }
        // return flag;
    }
}
