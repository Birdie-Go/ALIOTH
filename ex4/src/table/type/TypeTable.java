package table.type;
import java.util.*;

/**
 * 全局类型表
 */
public class TypeTable {
    /** 类型列表 */
    private ArrayList <Types> list;

    /** 构造函数 */
    public TypeTable() {
        list = new ArrayList<>();
    }

    /**
     * 新增一个类型
     * @param aNew 新增类型
     */
    public void add(Types aNew) {
        list.add(aNew);
    }

    /**
     * 根据类型名称查找一个类型
     * @param name 类型名称
     * @return 查找的类型
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
