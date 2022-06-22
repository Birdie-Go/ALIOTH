package table.procedure;
import java.util.*;

/**
 * moduleȫ�ֹ��̱�
 */
public class ProcedureTable {
    /** �����б� */
    private ArrayList <Procedures> list;

    /** ���캯�� */
    public ProcedureTable() {
        list = new ArrayList<>();
    }

    /**
     * ����һ������
     * @param aNew ����
     */
    public void add(Procedures aNew) {
        list.add(aNew);
    }

    /**
     * ���ݹ������Ʋ��ҹ���
     * @param name ������
     * @return ����
     */
    public Procedures find(String name) {
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).getName().equals(name))
                return list.get(i);
        return null;
    }
}
