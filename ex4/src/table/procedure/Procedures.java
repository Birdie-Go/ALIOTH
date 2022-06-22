package table.procedure;
import java.util.*;
import table.type.*;
import table.var.*;

/**
 * 过程类, 表示一个过程
 */
public class Procedures {
    /** 过程名 */
    private String name;
    /** 过程定义的变量表 */
    private ArrayList <Vars> varList;
    /** 过程定义的类型表 */
    private ArrayList <Types> typeList;
    /** 过程的参数列表 */
    private ArrayList <Types> parameterList;
    /** 过程定义的过程表 */
    private ArrayList <Procedures> functionList;
    
    /**
     * 构造函数
     * @param _name 过程名
     */
    public Procedures(String _name) {
        name = _name;
        varList = new ArrayList<>();
        typeList = new ArrayList<>();
        parameterList = new ArrayList<>();
        functionList = new ArrayList<>();
    }

    /**
     * 获取过程名称
     * @return String, 过程名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取参数列表长度
     * @return int, 参数列表长度
     */
    public int parameterLength() {
        return parameterList.size();
    }

    /**
     * 获取第i个参数
     * @param i i
     * @return Types, 第i个参数
     */
    public Types getParameteri(int i) {
        return parameterList.get(i);
    }

    /**
     * 新增一个过程定义变量
     * @param aNew 过程定义变量
     */
    public void addVar(Vars aNew) {
        varList.add(aNew);
    }

    /**
     * 新增一个过程定义的类型
     * @param aNew 过程定义的类型
     */
    public void addType(Types aNew) {
        typeList.add(aNew);
    }

    /**
     * 新增一个形参
     * @param aNew 形参
     */
    public void addParameter(Types aNew) {
        parameterList.add(aNew);
    }

    /**
     * 添加一个子过程
     * @param aNew 子过程
     */
    public void addProcedure(Procedures aNew) {
        functionList.add(aNew);
    }

    /**
     * 根据name查询定义的类型
     * @param name 类型名称
     * @return 类型
     */
    public TypeType findType(String name) {
        for (int i = 0; i < typeList.size(); i++) {
            if (typeList.get(i).getType().equals("type")) {
                TypeType temp = (TypeType)typeList.get(i);
                if (temp.getTypeName().equals(name))
                    return temp;
            }
        }
        return null;
    }

    /**
     * 根据变量名查询变量
     * @param id 变量名
     * @return 变量
     */
    public Vars findVar(String id) {
        for (int i = 0; i < varList.size(); i++) {
            if (varList.get(i).getName().equals(id))
                return varList.get(i);
        }
        return null;
    }
}
