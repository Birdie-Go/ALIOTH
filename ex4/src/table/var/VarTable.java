package table.var;
import java.util.*;

/**
 * ȫ�ֱ�����
 */
public class VarTable {
    /** �����б� */
    private ArrayList <Vars> list;
    
    /** ���캯�� */
    public VarTable() {
        list = new ArrayList<>();
    }

    /**
     * ����һ������
     * @param aNew �±���
     */
    public void add(Vars aNew) {
        list.add(aNew);
    }

    /**
     * ���ݱ��������ұ���
     * @param id ������
     * @return ��ѯ�ı���
     */
    public Vars findVar(String id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(id))
                return list.get(i);
        }
        return null;
    }
}
