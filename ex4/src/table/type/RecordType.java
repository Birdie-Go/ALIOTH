package table.type;
import java.util.*;
import table.var.*;

/**
 * Record类型
 */
public class RecordType extends Types {
    /** 参数表 */
    private ArrayList <Vars> son;
    /** 构造函数 */
    public RecordType() {
        name = "record";
        son = new ArrayList<>();
    }

    /**
     * 获取所有的参数
     * @return 参数列表
     */
    public ArrayList <Vars> getSon() {
        return son;
    }

    /**
     * 新增一个参数
     * @param aNew 参数
     */
    public void add(Vars aNew) {
        son.add(aNew);
    }

    /**
     * 根据名称查找某一个变量
     * @param id 变量名
     * @return 变量
     */
    public Vars findSon(String id) {
        for (int i = 0; i < son.size(); i++)
            if (son.get(i).getName().equals(id))
                return son.get(i);
        return null;
    }

    /**
     * 判断两个结构体是否同一类型
     * @param a 结构体a
     * @param b 结构体b
     * @return 是否同一类型
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
