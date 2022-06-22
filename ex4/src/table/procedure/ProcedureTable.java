package table.procedure;
import java.util.*;

/**
 * module全局过程表
 */
public class ProcedureTable {
    /** 过程列表 */
    private ArrayList <Procedures> list;

    /** 构造函数 */
    public ProcedureTable() {
        list = new ArrayList<>();
    }

    /**
     * 新增一个过程
     * @param aNew 过程
     */
    public void add(Procedures aNew) {
        list.add(aNew);
    }

    /**
     * 根据过程名称查找过程
     * @param name 过程名
     * @return 过程
     */
    public Procedures find(String name) {
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).getName().equals(name))
                return list.get(i);
        return null;
    }
}
