package table.type;
import java.util.*;

/**
 * ȫ�����ͱ�
 */
public class TypeTable {
    /** �����б� */
    private ArrayList <Types> list;

    /** ���캯�� */
    public TypeTable() {
        list = new ArrayList<>();
    }

    /**
     * ����һ������
     * @param aNew ��������
     */
    public void add(Types aNew) {
        list.add(aNew);
    }

    /**
     * �����������Ʋ���һ������
     * @param name ��������
     * @return ���ҵ�����
     */
    public TypeType findType(String name) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType().equals("type")) {
                TypeType temp = (TypeType)list.get(i);
                if (temp.getTypeName().equals(name))
                    return temp;
            }
        }
        return null;
    }
}
