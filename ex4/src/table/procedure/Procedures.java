package table.procedure;
import java.util.*;
import table.type.*;
import table.var.*;

/**
 * ������, ��ʾһ������
 */
public class Procedures {
    /** ������ */
    private String name;
    /** ���̶���ı����� */
    private ArrayList <Vars> varList;
    /** ���̶�������ͱ� */
    private ArrayList <Types> typeList;
    /** ���̵Ĳ����б� */
    private ArrayList <Types> parameterList;
    /** ���̶���Ĺ��̱� */
    private ArrayList <Procedures> functionList;
    
    /**
     * ���캯��
     * @param _name ������
     */
    public Procedures(String _name) {
        name = _name;
        varList = new ArrayList<>();
        typeList = new ArrayList<>();
        parameterList = new ArrayList<>();
        functionList = new ArrayList<>();
    }

    /**
     * ��ȡ��������
     * @return String, ��������
     */
    public String getName() {
        return name;
    }

    /**
     * ��ȡ�����б���
     * @return int, �����б���
     */
    public int parameterLength() {
        return parameterList.size();
    }

    /**
     * ��ȡ��i������
     * @param i i
     * @return Types, ��i������
     */
    public Types getParameteri(int i) {
        return parameterList.get(i);
    }

    /**
     * ����һ�����̶������
     * @param aNew ���̶������
     */
    public void addVar(Vars aNew) {
        varList.add(aNew);
    }

    /**
     * ����һ�����̶��������
     * @param aNew ���̶��������
     */
    public void addType(Types aNew) {
        typeList.add(aNew);
    }

    /**
     * ����һ���β�
     * @param aNew �β�
     */
    public void addParameter(Types aNew) {
        parameterList.add(aNew);
    }

    /**
     * ���һ���ӹ���
     * @param aNew �ӹ���
     */
    public void addProcedure(Procedures aNew) {
        functionList.add(aNew);
    }

    /**
     * ����name��ѯ���������
     * @param name ��������
     * @return ����
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
     * ���ݱ�������ѯ����
     * @param id ������
     * @return ����
     */
    public Vars findVar(String id) {
        for (int i = 0; i < varList.size(); i++) {
            if (varList.get(i).getName().equals(id))
                return varList.get(i);
        }
        return null;
    }
}
