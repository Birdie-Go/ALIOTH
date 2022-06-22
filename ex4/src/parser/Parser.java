package parser;

import scanner.*;
import exceptions.*;
import table.var.*;
import table.type.*;
import table.procedure.*;

import java.util.*;

/**
 * 语法和语义Parser
 */
public class Parser {
    /** lookahead */
    private Symbols lookahead;
    /** scanner */
    private MyScanner scanner;
    /** 变量表 */
    private VarTable varTable;
    /** 类型表 */
    private TypeTable typeTable;
    /** 调用链 */
    private ArrayList <Procedures> list;
    /** 过程表 */
    private ProcedureTable preProcedure;
    /** if层数 */
    private int ifCount;
    /** while层数 */
    private int whileCount;

    /**
     * 构造函数，获取第一个lookahead
     * @param _scanner scanner
     * @throws java.io.IOException IOException
     * @throws LexicalException LexicalException
     */
    public Parser(MyScanner _scanner) throws java.io.IOException, LexicalException {
        scanner = _scanner;
        lookahead = scanner.next_token();
        varTable = new VarTable();
        typeTable = new TypeTable();
        preProcedure = new ProcedureTable();
        list = new ArrayList<>();
    }

    /**
     * 获取当前lookahead的行号
     * @return int, 行号
     */
    public int getLine() {
        return lookahead.getLine();
    }

    /**
     * 获取当前lookahead的列号
     * @return int, 列号
     */
    public int getColumn() {
        return lookahead.getColumn();
    }

    /**
     * parse入口
     * @return 代码的复杂度
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    public int parse() throws java.io.IOException, OberonException {
        return modulesBlock();
    }

    /**
     * 匹配一个lookahead, 并获取下一个lookahead
     * @param token 待匹配的token
     * @return String, lookahead's value
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private String match(String token) throws java.io.IOException, OberonException {
        
        if (lookahead.getToken().equals(token)) {
            String value = lookahead.getValue();
            lookahead = scanner.next_token();
            return value;
        }

        switch (token) {
            case "(":
                throw new MissingLeftParenthesisException();
            case ")":
                throw new MissingRightParenthesisException();
            case "IDENTIFIER":
            case "NUMBER":
                throw new MissingOperandException();
            default:
                System.out.println("期望 \'" + token + "\', but not.");
                throw new SyntacticException();
        }
    }

    /**
     * 判断lookahead是不是token
     * @param token 待判断的字符串
     * @return 是否匹配成功
     */
    private boolean is(String token) {
        return lookahead.getToken().equals(token);
    }

    /**
     * 根据变量名查找变量
     * @param id 带查找的变量名
     * @return Vars, result
     */
    private Vars findVar(String id) {
        for (int i = list.size() - 1; i >= 0; i--) {
            Vars result = list.get(i).findVar(id);
            if (result != null)
                return result;
        }
        
        Vars result = varTable.findVar(id);
        if (result == null) {
            System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
            System.out.println("SyntacticException : 变量不存在");
            return new Vars("", new IntType(), false);
        }
        return result;
    }

    /**
     * modulesBlock
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int modulesBlock() throws java.io.IOException, OberonException {
        
        match("MODULE");
        String id1 = match("IDENTIFIER");
        match(";");
        int e1 = declarations();
        int e2 = beginStatementSequence();
        match("END");
        String id2 = match("IDENTIFIER");
        match(".");

        if (is("_")) {
            System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
            System.out.println("SyntacticException : 期望 EOF, but not.");
        }

        if (!id1.equals(id2)) {
            System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
            System.out.println("SyntacticException : 期望 " + id1 + ", but not.");
        }
        return e1 + e2;
    }

    /**
     * beginStatementSequence
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int beginStatementSequence() throws java.io.IOException, OberonException {
        
        if (is("BEGIN")) {
            match("BEGIN");
            int e = statementSequence();
            return e;
        }
        return 0;
    }

    /**
     * declarations
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int declarations() throws java.io.IOException, OberonException {
        
        int e1 = constBlock();
        int e2 = typeBlock();
        int e3 = varBlock();
        int e4 = procedureDeclarations();
        
        return e1 + e2 + e3 + e4;
    }

    /**
     * constBlock
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int constBlock() throws java.io.IOException, OberonException {
        
        if (is("CONST")) {
            match("CONST");
            int e = identifierExpressions();
            return 5 + e;
        }
        return 0;
    }

    /**
     * identifierExpressions
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int identifierExpressions() throws java.io.IOException, OberonException {
        
        if (is("IDENTIFIER")) {
            int e1 = identifierExpression();
            int e2 = identifierExpressions();
            return e1 + e2;
        }
        return 0;
    }

    /**
     * identifierExpression
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int identifierExpression() throws java.io.IOException, OberonException {
        
        String id = match("IDENTIFIER");
        match("=");
        Pairs <Vars, Integer> exp = expression();
        match(";");

        if (list.size() == 0)
            varTable.add(new Vars(id, exp.first, true));
        else
            list.get(list.size()-1).addVar(new Vars(id, exp.first, true));
        return 1 + exp.second;
    }

    /**
     * typeBlock
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int typeBlock() throws java.io.IOException, OberonException {
        
        if (is("TYPE")) {
            match("TYPE");
            int e = identifierTypes();
            return 10 + e;
        }
        return 0;
    }

    /**
     * identifierTypes
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int identifierTypes() throws java.io.IOException, OberonException {
        
        if (is("IDENTIFIER")) {
            int e1 = identifierType();
            int e2 = identifierTypes();
            return e1 + e2;
        }
        return 0;
    }

    /**
     * identifierType
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int identifierType() throws java.io.IOException, OberonException {
        
        String id = match("IDENTIFIER");
        match("=");
        Pairs <Types, Integer> type = types();
        match(";");

        if (list.size() == 0)
            typeTable.add(new TypeType(id, type.first));
        else
            list.get(list.size()-1).addType(new TypeType(id, type.first));
        return 1 + type.second;
    }

    /**
     * varBlock
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int varBlock() throws java.io.IOException, OberonException {
        
        if (is("VAR")) {
            match("VAR");
            int e = identifierListTypes();
            return e;
        }
        return 0;
    }

    /**
     * identifierListTypes
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int identifierListTypes() throws java.io.IOException, OberonException {
        
        if (is("IDENTIFIER")) {
            int e1 = identifierListType();
            int e2 = identifierListTypes();
            return e1 + e2;
        }
        return 0;
    }

    /**
     * identifierListType
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int identifierListType() throws java.io.IOException, OberonException {
        
        ArrayList <String> vars = identifierList();
        match(":");
        Pairs <Types, Integer> type = types();
        match(";");

        for (int i = 0; i < vars.size(); i++) {
            if (list.size() == 0)
                varTable.add(new Vars(vars.get(i), type.first, false));
            else {
                
                list.get(list.size()-1).addVar(new Vars(vars.get(i), type.first, false));
            }
        }

        return vars.size() + type.second;
    }

    /**
     * procedureDeclarations
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int procedureDeclarations() throws java.io.IOException, OberonException {
        
        if (is("PROCEDURE")) {
            int e1 = procedureDeclarationBlock();
            int e2 = procedureDeclarations();
            return e1 + e2;
        }
        return 0;
    }

    /**
     * procedureDeclarationBlock
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int procedureDeclarationBlock() throws java.io.IOException, OberonException {
        
        int e = procedureDeclaration();
        match(";");
        return e;
    }

    /**
     * procedureDeclaration
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int procedureDeclaration() throws java.io.IOException, OberonException {
        
        int e1 = procedureHeading();
        match(";");
        int e2 = procedureBody();
        
        return e1 + e2;
    }

    /**
     * procedureHeading
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int procedureHeading() throws java.io.IOException, OberonException {
        
        match("PROCEDURE");
        String id = match("IDENTIFIER");

        list.add(new Procedures(id));

        int e = formalParametersBlock();
        return 20 + e;
    }

    /**
     * procedureBody
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int procedureBody() throws java.io.IOException, OberonException {
        
        int e1 = declarations();
        int e2 = beginStatementSequence();
        
        match("END");
        String id = match("IDENTIFIER");

        if (!list.get(list.size()-1).getName().equals(id)) {
            System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
            System.out.println("SyntacticException : 期望 " + list.get(list.size()-1).getName() + ", but not.");
        }
        
        preProcedure.add(list.get(list.size() - 1));
        list.remove(list.size() - 1);
        return e1 + e2;
    }

    /**
     * formalParametersBlock
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int formalParametersBlock() throws java.io.IOException, OberonException {
        
        if (is("(")) {
            int e = formalParameters();
            return e;
        }
        return 0;
    }

    /**
     * formalParameters
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int formalParameters() throws java.io.IOException, OberonException {
        
        match("(");
        int e = fpSectionBlock();
        match(")");
        return e;
    }

    /**
     * fpSectionBlock
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int fpSectionBlock() throws java.io.IOException, OberonException {
        
        if (is("VAR") || is("IDENTIFIER")) {
            int e1 = fpSection();
            int e2 = semiFpSection();
            return e1 + e2;
        }
        return 0;
    }

    /**
     * semiFpSection
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int semiFpSection() throws java.io.IOException, OberonException {
        
        if (is(";")) {
            match(";");
            int e1 = fpSection();
            int e2 = semiFpSection();
            return e1 + e2;
        }
        return 0;
    }

    /**
     * fpSection
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int fpSection() throws java.io.IOException, OberonException {
        
        if (is("VAR"))
            match("VAR");

        ArrayList <String> vars = identifierList();
        match(":");
        Pairs <Types, Integer> type = types();

        for (int i = 0; i < vars.size(); i++) {
            if (list.size() == 0)
                varTable.add(new Vars(vars.get(i), type.first, false));
            else {
                list.get(list.size()-1).addVar(new Vars(vars.get(i), type.first, false));
            }
            list.get(list.size()-1).addParameter(type.first);
        }

        return vars.size() + type.second;
    }

    /**
     * fpSection
     * @return pair of (types, complexity)
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private Pairs <Types, Integer> types() throws java.io.IOException, OberonException {
        
        if (is("IDENTIFIER")) {

            String id = match("IDENTIFIER");
            for (int i = list.size() - 1; i >= 0; i--) {
                TypeType result = list.get(i).findType(id);
                if (result != null)
                    return new Pairs <Types, Integer>(result, 2);
            }
            
            TypeType result = typeTable.findType(id);
            if (result != null)
                return new Pairs <Types, Integer>(result, 2);
            
            System.out.println("未识别标识符" + id);
            throw new SyntacticException();

        } else if (is("ARRAY")) {

            return arrayType();

        } else if (is("RECORD")) {

            return recordType();

        } else if (is("INTEGER")) {

            match("INTEGER");
            return new Pairs <Types, Integer>(new IntType(), 1);

        } else if (is("BOOLEAN")) {

            match("BOOLEAN");
            return new Pairs <Types, Integer>(new BoolType(), 1);

        }

        System.out.println("未识别该类型.");
        throw new SyntacticException();
    }

    /**
     * recordType
     * @return record type
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private Pairs <Types, Integer> recordType() throws java.io.IOException, OberonException {
        
        match("RECORD");
        RecordType now = new RecordType();
        int e1 = fieldListBlock(now);
        int e2 = semiFieldLists(now);
        match("END");
        return new Pairs <Types, Integer> (now, 3 + e1 + e2);
    }

    /**
     * semiFieldLists
     * @return complexity
     * @param now record
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int semiFieldLists(RecordType now) throws java.io.IOException, OberonException {
        
        
        if (is(";")) {
            match(";");
            int e1 = fieldListBlock(now);
            int e2 = semiFieldLists(now);
            return e1 + e2;
        }
        return 0;
    }

    /**
     * fieldListBlock
     * @return comlexity
     * @param now record
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int fieldListBlock(RecordType now) throws java.io.IOException, OberonException {
        
        if (is("IDENTIFIER")) {
            
            ArrayList <String> vars = identifierList();
            match(":");
            Pairs <Types, Integer> type = types();

            for (int i = 0; i < vars.size(); i++)
                now.add(new Vars(vars.get(i), type.first, false));
            return vars.size() + type.second;
        }
        return 0;
    }

    /**
     * arrayType
     * @return array type
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private Pairs <Types, Integer> arrayType() throws java.io.IOException, OberonException {
        
        match("ARRAY");
        Pairs <Vars, Integer> exp = expression();
        match("OF");
        Pairs <Types, Integer> type = types();

        if (!exp.first.getType().getType().equals("int")) {
            System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
            System.out.println("TypeMismatchedException : 数组范围必须为INTEGER类型");
        }
        ArrayType aNew = new ArrayType(0, type.first); 
        return new Pairs <Types, Integer>(aNew, 8 + exp.second + type.second);
    }

    /**
     * identifierList
     * @return ArrayList of identifier
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private ArrayList <String> identifierList() throws java.io.IOException, OberonException {
        
        ArrayList <String> id = new ArrayList<>();
        id.add(new String(match("IDENTIFIER")));
        while (is(",")) {
            match(",");
            id.add(new String(match("IDENTIFIER")));
        }
        return id;
    }

    /**
     * statementSequence
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int statementSequence() throws java.io.IOException, OberonException {
        
        int e = 0;
        if (is("IDENTIFIER")) {

            String id = match("IDENTIFIER");
            
            if (is(".") || is("[") || is(":=")) {
                e = assignment(id);
            }
            else
                e = procedureCall(id); 
            
        } else if (is("IF"))
            e = ifStatement();
        else if (is("WHILE"))
            e = whileStatement();
        else if (is("READ"))
            e = readBlock();
        else if (is("WRITE"))
            e = writeBlock();
        else if (is("WRITELN"))
            e = writelnBlock();

        if (is(";")) {
            match(";");
            e += statementSequence();
        }
        
        return e;
    }

    /**
     * readBlock
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int readBlock() throws java.io.IOException, OberonException {
        
        match("READ");
        int e = actualParametersBlock("READ");
        return 8 * (1 + ifCount) * (1 << whileCount) + e;
    }

    /**
     * writeBlock
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int writeBlock() throws java.io.IOException, OberonException {
        
        match("WRITE");
        int e = actualParametersBlock("WRITE");
        return 8 * (1 + ifCount) * (1 << whileCount) + e;
    }

    /**
     * writelnBlock
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int writelnBlock() throws java.io.IOException, OberonException {
        
        match("WRITELN");
        int e = actualParametersBlock("WRITELN");
        return 8 * (1 + ifCount) * (1 << whileCount) + e;
    }

    /**
     * whileStatement
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int whileStatement() throws java.io.IOException, OberonException {
        
        match("WHILE");
        whileCount += 1;
        Pairs <Vars, Integer> exp = expression();
        if (!exp.first.getType().getType().equals("bool")) {
            System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
            System.out.println("TypeMismatchedException : 表达式的值需为boolean类型");
        }
        match("DO");
        int e = statementSequence();
        match("END");
        whileCount -= 1;
        
        return exp.second + e;
    }

    /**
     * ifStatement
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int ifStatement() throws java.io.IOException, OberonException {
        
        match("IF");
        ifCount += 1;
        Pairs <Vars, Integer> exp = expression();
        if (!exp.first.getType().getType().equals("bool")) {
            System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
            System.out.println("TypeMismatchedException : 表达式的值需为boolean类型");
        }
        match("THEN");
        int e1 = statementSequence();
        int e2 = elsifBlock();
        int e3 = elseBlock();
        match("END");
        ifCount -= 1;
        return exp.second + e1 + e2 + e3;
    }

    /**
     * elsifBlock
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int elsifBlock() throws java.io.IOException, OberonException {
        
        if (is("ELSIF")) {
            match("ELSIF");
            Pairs <Vars, Integer> exp = expression();
            if (!exp.first.getType().getType().equals("bool")) {
                System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
                System.out.println("TypeMismatchedException : 表达式的值需为boolean类型");
            }
            match("THEN");
            int e = statementSequence();
            return exp.second + e;
        }
        return 0;
    }

    /**
     * elseBlock
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int elseBlock() throws java.io.IOException, OberonException {
        
        if (is("ELSE")) {
            match("ELSE");
            int e = statementSequence();
            return e;
        }
        return 0;
    }

    /**
     * procedureCall
     * @param id identifier
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int procedureCall(String id) throws java.io.IOException, OberonException {
        
        int e = actualParametersBlock(id);
        
        return 8 * (1 + ifCount) * (1 << whileCount) + e;
    }

    /**
     * actualParametersBlock
     * @param name procedure name
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int actualParametersBlock(String name) throws java.io.IOException, OberonException {
        
        ArrayList <Vars> exps = new ArrayList<>();
        int score = 0;

        if (is("(")) {
            match("(");
            if (!is(")")) {
                Pairs <Vars, Integer> exp = expression();
                exps.add(exp.first);
                score += exp.second;
            }
            while (!is(")")) {
                match(",");
                Pairs <Vars, Integer> exp = expression();
                exps.add(exp.first);
                score += exp.second;
            }
            
            match(")");
        }

        if (name.equals("READ") || name.equals("WRITE") || name.equals("WRITELN"))
            return score;

        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).getName().equals(name)) {

                if (exps.size() != list.get(i).parameterLength()) {

                    System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
                    System.out.println("SemanticException : ParameterMismatchedException.");
                    System.out.println("Except " + list.get(i).parameterLength() + " but " + exps.size());

                } else {

                    boolean flag = true;
                    for (int j = 0; j < exps.size(); j++) {
                        flag &= (Types.equal(exps.get(j).getType(), list.get(i).getParameteri(j)));
                    }
                    if (!flag) {
                        System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
                        System.out.println("SemanticException : TypeMismatchedException.");
                    }

                }
                return score;

            }
        }

        Procedures target = preProcedure.find(name);
        if (target == null) {

            System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
            System.out.println("SyntacticException : Not found " + name);
        } else {

            if (exps.size() != target.parameterLength()) {

                System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
                System.out.println("SemanticException : ParameterMismatchedException.");
                System.out.println("Except " + target.parameterLength() + " but " + exps.size());

            } else {

                boolean flag = true;
                for (int j = 0; j < exps.size(); j++) {
                    flag &= (Types.equal(exps.get(j).getType(), target.getParameteri(j)));
                }
                if (!flag) {
                    System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
                    System.out.println("SemanticException : TypeMismatchedException.");
                }

            }
        }

        return score;
    }

    /**
     * assignment
     * @param id identifier
     * @return complexity
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private int assignment(String id) throws java.io.IOException, OberonException {
        
        Vars var = findVar(id);
        if (var.isItConst()) {
            System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
            System.out.println("SemanticException : 常量不能作为左值.");
        }
        Pairs <Types, Integer> type = selectorBlock(var);
        match(":=");
        
        Pairs <Vars, Integer> exp = expression();
        
        
        if (!Types.equal(type.first, exp.first.getType())) {
            System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
            System.out.println("TypeMismatchedException : 赋值语句两边类型不一致.");
        }
        
        return (type.second + 2) * (1 + ifCount) * (1 << whileCount) + exp.second;
    }

    /**
     * expression
     * @return Vars, expression result
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private Pairs <Vars, Integer> expression() throws java.io.IOException, OberonException {
        
        
        Pairs <Vars, Integer> left = usimpleExpression();
        
        boolean flag = true;
        if (is("=")) {
            match("=");
            flag = false;
        }
        else if (is("#")) {
            match("#");
            flag =false;
        }
        else if (is("<"))
            match("<");
        else if (is(">"))
            match(">");
        else if (is("<="))
            match("<=");
        else if (is(">="))
            match(">=");
        else {
            int score = left.second * (1 + ifCount) * (1 << whileCount);
            return new Pairs <Vars, Integer>(left.first, score);
        }
        
        Pairs <Vars, Integer> right = usimpleExpression();
        if (!Vars.sameType(left.first, right.first)) {
            System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
            System.out.println("TypeMismatchedException : 表达式两边的类型不一致.");
        }
        if (flag && !left.first.getType().getType().equals("int")) {
            System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
            System.out.println("TypeMismatchedException : 类型需为integer.");
        }
        int score = (left.second + right.second + 4) * (1 + ifCount) * (1 << whileCount);
        return new Pairs <Vars, Integer>(new Vars("", new BoolType(), true), score);
    }

    /**
     * usimpleExpression
     * @return Vars, expression result
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private Pairs <Vars, Integer> usimpleExpression() throws java.io.IOException, OberonException {
        
        int add = 0;
        if (is("+")) {
            match("+");
            add = 2;
        }
        else if (is("-")) {
            match("-");
            add = 2;
        }
        Pairs <Vars, Integer> temp = simpleExpression();
        
        return new Pairs <Vars, Integer>(temp.first, temp.second + add);
    }

    /**
     * simpleExpression
     * @return Vars, expression result
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private Pairs <Vars, Integer> simpleExpression() throws java.io.IOException, OberonException {
        
        Pairs <Vars, Integer> result = term();
        int score = result.second;
        while (is("+") || is("-") || is("OR")) {
            if (is("+")) {

                match("+");
                Pairs <Vars, Integer> another = term();
                if (!Vars.sameType(result.first, another.first)
                    || !result.first.getType().getType().equals("int")) {
                    System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
                    System.out.println("TypeMismatchedException : 表达式的值需为integer类型");
                }
                score += another.second + 2;

            } else if (is("-")) {

                match("-");
                Pairs <Vars, Integer> another = term();
                if (!Vars.sameType(result.first, another.first)
                    || !result.first.getType().getType().equals("int")) {
                    System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
                    System.out.println("TypeMismatchedException : 表达式的值需为integer类型");
                }
                score += another.second + 2;

            } else if (is("OR")) {

                match("OR");
                Pairs <Vars, Integer> another = term();
                if (!Vars.sameType(result.first, another.first)
                    || !result.first.getType().getType().equals("bool")) {
                    System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
                    System.out.println("TypeMismatchedException : 表达式的值需为boolean类型");
                }
                score += another.second + 6;

            }
        }
        
        return new Pairs <Vars, Integer> (result.first, score);
    }

    /**
     * term
     * @return Vars, expression result
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private Pairs <Vars, Integer> term() throws java.io.IOException, OberonException {
        
        Pairs <Vars, Integer> result = factor();
        int score = result.second;
        while (is("*") || is("DIV") || is("MOD") || is("&")) {
            if (is("*")) {

                match("*");
                Pairs <Vars, Integer> another = factor();
                if (!Vars.sameType(result.first, another.first)
                    || !result.first.getType().getType().equals("int")) {
                    System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
                    System.out.println("TypeMismatchedException : 表达式的值需为integer类型");
                }
                score += another.second + 4;

            } else if (is("DIV")) {

                match("DIV");
                Pairs <Vars, Integer> another = factor();
                if (!Vars.sameType(result.first, another.first)
                    || !result.first.getType().getType().equals("int")) {
                    System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
                    System.out.println("TypeMismatchedException : 表达式的值需为integer类型");
                }
                score += another.second + 4;

            } else if (is("MOD")) {

                match("MOD");
                Pairs <Vars, Integer> another = factor();
                if (!Vars.sameType(result.first, another.first)
                    || !result.first.getType().getType().equals("int")) {
                    System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
                    System.out.println("TypeMismatchedException : 表达式的值需为integer类型");
                }
                score += another.second + 4;

            } else if (is("&")) {

                match("&");
                Pairs <Vars, Integer> another = factor();
                if (!Vars.sameType(result.first, another.first)
                    || !result.first.getType().getType().equals("bool")) {
                        
                    System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
                    System.out.println("TypeMismatchedException : 表达式的值需为boolean类型");
                }
                score += another.second + 6;

            }
        }
        
        return new Pairs <Vars, Integer>(result.first, score);
    }

    /**
     * factor
     * @return Vars, expression result
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private Pairs <Vars, Integer> factor() throws java.io.IOException, OberonException {
        
        if (is("IDENTIFIER")) {

            String id = match("IDENTIFIER");
            Vars var = findVar(id);
            Pairs <Types, Integer> type = selectorBlock(var);
            while (type.first.getType().equals("type"))
                type = new Pairs <Types, Integer>(((TypeType)type.first).getTypeType(), type.second);
            return new Pairs <Vars, Integer>(new Vars("", type.first, var.isItConst()), type.second);

        } else if (is("NUMBER")) {

            match("NUMBER");
            return new Pairs <Vars, Integer>(new Vars("", new IntType(), true), 0);

        } else if (is("(")) {

            match("(");
            Pairs <Vars, Integer> exp = expression();
            match(")");
            int score = exp.second / (1 + ifCount) / (1 << whileCount) + 6;
            return new Pairs <Vars, Integer>(exp.first, score);

        } else if (is("~")) {

            match("~");
            Pairs <Vars, Integer> result = factor();
            if (!result.first.getType().getType().equals("bool")) {
                System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
                System.out.println("TypeMismatchedException : 表达式的值需为boolean类型");
            }
            int score = 6 + result.second;
            return new Pairs <Vars, Integer>(result.first, score);

        }
        throw new MissingOperandException();
    }

    /**
     * selectorBlock
     * @param var var to select block
     * @return Types, select result
     * @throws java.io.IOException IOException
     * @throws OberonException OberonException
     */
    private Pairs <Types, Integer> selectorBlock(Vars var) throws java.io.IOException, OberonException {
        
        Types type = var.getType();
        int score = 0;
        while (is(".") || is("[")) {
            if (is(".")) {
                match(".");
                String id = match("IDENTIFIER");
                
                type = ((RecordType)type).findSon(id).getType();
                
                score += 2;
            } else if (is("[")) {
                match("[");
                Pairs <Vars, Integer> exp = expression();
                match("]");
                if (!exp.first.getType().getType().equals("int")) {
                    System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
                    System.out.println("TypeMismatchedException : 表达式的值需为integer类型");
                }
                
                if (!type.getType().equals("array")) {
                    System.out.println("Error at (" + getLine() + "," + getColumn() + ")");
                    System.out.println("TypeMismatchedException: 该变量不为数组");
                } else {
                    type = ((ArrayType)type).getArrayType();
                }
                score += 2 + exp.second / (1 + ifCount) / (1 << whileCount);;
            }
        }
        return new Pairs <Types, Integer>(type, score);
    }
}
