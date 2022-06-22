package table.var;
import java.util.*;

/**
 * 全局变量表
 */
public class VarTable {
    /** 变量列表 */
    private ArrayList <Vars> list;
    
    /** 构造函数 */
    public VarTable() {
        list = new ArrayList<>();
    }

    /**
     * 新增一个变量
     * @param aNew 新变量
     */
    public void add(Vars aNew) {
        list.add(aNew);
    }

    /**
     * 根据变量名查找变量
     * @param id 变量名
     * @return 查询的变量
     */
    public Vars findVar(String id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(id))
                return list.get(i);
        }
        return null;
    }
}
