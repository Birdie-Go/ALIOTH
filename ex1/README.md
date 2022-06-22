

<center><b><font size = "7">编译原理实验





<center><b><font size = "5">复杂性度量工具 ALIOTH



<center><b><font size = "5">实验报告Part 1

<center><b><font size = "5">熟悉Oberon-0语言定义


### 1 开发环境与开发工具

#### 1.1 操作系统

Windows 11

#### 1.2 编程语言

java语言，JDK版本1.7.2

#### 1.3 开发工具

Visual Studio Code + cmd



### 2 实验内容

#### 2.1 编写一个正确的 Oberon-0 源程序

> 遵循 Oberon-0 语言的 BNF 定义，编写一个正确的 Oberon-0 源程序。要求在这个源程序中，用到 Oberon-0 语言的所有语法构造，即你编写的源程序覆盖了 Oberon-0 语言提供的模块、声明（类型、常量、变量 等）、过程声明与调用、语句、表达式等各种构造。 
>
> 如果有可能，你编写的 Oberon-0 源程序最好是有其实际意义的，譬如一个求阶乘的程序、一个求最大公因子的程序、一个用加法实现乘法的程序等等，但这只是一个任选的要求。 
>
> 注意，这里仅要求你编写一个词法、语法和语义符合 Oberon-0 语言定义的源程序，并未强制要求该源程序在逻辑上是正确的。

##### 正确的代码

过程 gcd 用于计算两个整数的最大公约数；过程 primefactor 用于分解质因数，并判断质因子是否为 2。

```
MODULE math;
    CONST zero = 0;
    TYPE bool = BOOLEAN;

    (* greatest common divisor *)
    PROCEDURE gcd(a : INTEGER; b : INTEGER);
        VAR r : INTEGER;
    BEGIN
        r := a MOD b;
        WHILE r # 0 DO
            a := b;
            b := r;
            r := a MOD b;
        END;
        WRITE(b)
    END gcd;

    (* Factorization prime factor *)
    PROCEDURE primefactor(a : INTEGER);
        VAR fac : ARRAY 100 OF RECORD
                num : INTEGER;
                is2 : bool
            END;
            i, j : INTEGER;

    BEGIN
        i := 2;
        j := zero;
        WHILE ~(a <= 1) DO
            WHILE a mod i = zero DO
                j := j + 1;
                fac[j].num := i;
                fac[j].is2 := (i = 2);
                a := a div i
            END;
            i := i + 1
        END;
        IF (i * j = zero) OR (i = 0) THEN
            WRITE(ZERO)
        ELSIF fac[j].is2 & (fac[j].num = 2) THEN
            gcd(i, j)
        ELSE
            write(j)
        END
    END primefactor;

END math.
```

##### 覆盖内容

该代码几乎覆盖了 Oberon-0 中的所有构造。包括：

- 模块：`MODULE math; ... END math.`

- 声明：

  - 常量声明：`CONST zero = 0;`
  - 类型声明：`TYPE bool = BOOLEAN;`
  - 变量声明：`VAR r : INTEGER;`

- 过程定义：

  - 定义过程：``PROCEDURE gcd ... END gcd``

  - 参数列表：`PROCEDURE gcd(a : INTEGER; b : INTEGER);`

- 类型：

  - 标识符列表：`i, j : INTEGER`
  - 整形和布尔型：`num : INTEGER; is2 : bool`
  - 数组：`VAR fac : ARRAY 100 OF RECORD ... END;`
  - 结构体：`RECOED ... END;`

- 语句：

  - 赋值：`r := a MOD b;`
  - 过程调用：`gcd(i, j)`
  - 条件语句：`IF .. ELSIF .. ELSE .. END`
  - 循环语句：`WHILE .. DO END;`
  - 结构体和数组使用：`fac[j].is2 := ...`

- 表达式：`fac[j].is2 & (fac[j].num = 2)`

- 各种运算：`* DIV MOD + - & OR ~`

- 注释：`(* greatest common divisor *)`



#### 2.2 编写上述 Oberon-0 源程序的变异程序

> 根据上述正确的源程序，写出若干含有词法、语法或语义错误的Oberon-0 源程序。其做法是对原有的 正确源程序做出最小的改动，使之成为一个包含至少一个错误的源程序；为每一种可能存在的词法、语法和语义错误构造出一个变异程序。例如，有一个变异程序中含有不合法的标识符，有另一个变异程序中含有不合法的常量，还有一个变异程序中丢失了左括号等。在每一变异程序的第一行用注释说明该变异产生的错误类型。
>
> 基于极限编程 XP（eXtreme Programming）中的测试驱动编程（Test-Driven Programming）思想，在进入正式编码前先设计好测试用例将有助于把握正确的设计。而上述正确的 Oberon-0 源程序及其一批含有错误的变异程序将在后述实验步骤中作为测试用例。
>
> 上述“程序变异”的思路源于软件测试技术中的变异测试（Mutation Testing）。变异测试的目标是筛选更有效的测试数据，其思路是通过变异（Mutation）操作在程序中植入一个错误，变异后的程序称为变异程序（Mutant）；通过检查测试数据能否发现变异程序中的这些错误，可判断测试数据的有效性。有兴趣的同学不妨查阅相关资料（例如：http://en.wikipedia.org/wiki/Mutation_testing）进一步学习变异测试技术。

##### 词法错误

具体的位置信息见变异代码的头部注释。

- IllegalSymbolException（gcd.001）

  正确写法：`r := a MOD b;`

  变异写法：`r := a % b;`

- IllegalIntegerException（gcd.002）

  正确写法：`VAR r : INTEGER;`

  变异写法：`VAR 1r : INTEGER;`

- IllegalIntegerRangeException（gcd.003）

  正确写法：`i := 2;`

  变异写法：`i := 12345678901234;`

- IllegalOctalException（gcd.004）

  正确写法：`i := 2;`

  变异写法：`i := 02.2;`

- IllegalIdentifierLengthException（gcd.005）

  正确写法：`VAR r : INTEGER;`

  变异写法：`VAR abcdefghijklmnopqrstuvwxzy : INTEGER;`

- MismatchedCommentException（gcd.006）

  变异写法：没有`*)`

##### 语法错误

- MissingRightParenthesisException（gcd.007）

  正确写法：`PROCEDURE gcd(a : INTEGER; b : INTEGER);`

  变异写法：`PROCEDURE gcd(a : INTEGER; b : INTEGER;`

- MissingLeftParenthesisException（gcd.008）

  正确写法：`PROCEDURE gcd(a : INTEGER; b : INTEGER);`

  变异写法：`PROCEDURE gcda : INTEGER; b : INTEGER);`

- MissingOperatorException（gcd.009）

  正确写法：`j := j + 1;`

  变异写法：`j := j 1;`

- MissingOperandException（gcd.0010）

  正确写法：`j := j + 1;`

  变异写法：`j := j + ;`

##### 语义错误

- ParameterMismatchedException（gcd.011）

  正确写法：`gcd(i, j)`

  变异写法：`gcd(i)`

- TypeMismatchedException（gcd.012）

  正确写法：`a := a div i`

  变异写法：`a := a div (i = 2)`



#### 2.3 讨论 Oberon-0 语言的特点

> 在 Oberon-0 语言中，与其前辈 Pascal 语言一样区别了保留字（Reserved Word）与关键字（Keyword）两个概念。例如，IF、THEN、ELSIF 等是保留字，而 INTEGER、WRITE、WRITELN 则是关键字。试解释这两个概念的区别。
>
> 根据 Oberon-0 语言的 BNF 定义，Oberon-0 程序中的表达式语法规则与 Java、C/C++等常见语言的表达式有何不同之处？试简要写出它们的差别。

##### 保留字与关键字

1. 保留字，指在高级语言中已经定义过的字，程序员不能再将这些字作为变量名、过程名或函数名使用。

2. 关键字，是至在语言中有特定含义，成为语法中一部分的那些字。

两者的概念类似，但不完全相同。

- 保留字不一定是关键字，从字面上理解，在语言设立之初，设计者会预先保留很多已经定义过的字，但这些保留下来的字不一定是作为关键字存在的，也可能不具有实际的意义，但是未来很有可能会添加新的特性使其变成关键字，比如 java 中的 `goto`。
- 而关键字也未必是保留字，对于可能出现新增加的关键字，为了避免对已有工程的破坏，编译器不会新增加的所有关键字做编译检查，对比如 c++11 中的 `final`。这是 c++11 新增的特性，用来标识虚函数不能在子类中被覆盖，或一个类不能被继承。但是由于该字不在保留字当中，为了避免造成旧代码的编译问题，于是程序员仍然可以使用该关键字作为变量存在。

##### 表达式语法规则

1. Oberon-0 赋予了 `:=` 作为赋值语句的功能，严格作为一个规定好的操作，除此之外别无他用。而 C++ 不同，C++ 中用于赋值的 `=` 本身具有返回值，可以进行 `a=b=c` 等操作，而 Oberon-0 不允许写成 `a:=b:=c`。可以说，C++ 的表达式的功能更多。

2. Oberon-0 中类型是严格的，而在 C++ 中，常常会发生隐式类型转换。比如 `1+(1>2)`，在 Oberon-0 中会抛出语义错误的信息，因为加号两边的类型不匹配，而 C++ 会隐式地将 `1>2` 的结果转换为数值类型，从而可以进行加法操作。

3. Oberon-0 中的数值没有大小限制，而 C/C++/Java 中的数值类型均有上下界。比如如下代码：

   ```pascal
   short a, b, c;
   a = 32767;
   b = 1;
   c = a + b;
   ```

   实际的计算结果是 32768，而在 Oberon-0 中计算结果也确实是 32768。但在 C++ 中，其计算结果是 -32768。原因在于，C++ 贴近机器语言，其内部把操作数当作二进制数看待。但是 Oberon-0 没有这个限制。其父亲 Pascal 中也是如此，Pascal 贴近数学，尽量以数学的视角看待数值。

4. 另外，Oberon-0 对符号的定义也比较特别，其规定用 `DIV` 表示整除，而其他大多数语言一般采用 `/`。



#### 2.4 讨论 Oberon-0 文法定义的二义性

> 根据 Oberon-0 语言的 BNF 定义，讨论 Oberon-0 程序的二义性问题，即讨论根据上述 BNF 定义的上下文无关文法是否存在二义性。
>
> 如果你认为该文法存在二义性，则举例说明在什么地方会出现二义性，并探讨如何改造文法以消除二义性。
>
> 如果你认为该文法没有二义性，则请解释：为何在其他高级程序设计语言中常见的那些二义性问题在 Oberon-0 语言中并未出现？

##### 二义性探讨

抽出 Oberon-0 对表达式的定义

```
expression = simple_expression [("=" | "#" | "<" | "<=" | ">" | ">=") simple_expression];
simple_expression = ["+" | "-"] term {("+" | "-" | "OR") term};
term = factor {("*" | "DIV" | "MOD" | "&") factor};
factor = identifier selector | number | "(" expression ")" | "~" factor ;
```

该 BNF 定义隐式地表达了运算的优先级：

- 括号和 `~` 的优先级最高；
- 乘法、除法和与运算的优先级次之；
- 加法、减法、取负和或运算的优先级再低一级；
- 比较运算符的优先级最低。

同级之间的语法同样不具备二义性，每一级别的表达式只由下一个级别的表达式构成，因此，一个表达式只会有一棵语法树。

##### 与其他高级语言的比较

Oberon-0 语法中的没有二义性，另重要的一个原因是：Oberon-0 采用 `BEGIN` 和 `END` 表达每一个语句的作用域。正是因为每个语句的作用域表现很清晰，不存在 `IF` 等语句与 `ELSE` 的结合问题。因此，分支语句和循环语句在 Oberon-0 中不存在二义性。



### 3 实验体会

其实在一开始看这个实验的时候，并没有觉得实验有多复杂。按照往常的经验，一个实验一般是比较繁琐而不是困难。但是在真正操手这个实验的时候，我发现我还是天真了。

实验一的分析并不复杂，但到了实验二，难度就缓慢上来了。其实现在回想，实验二其实也并不复杂，困难的原因在于，`JFlex` 这个工具在现代用的人比较少，在网络上也鲜有资料。实验本身并不难，但在实验前需要学习一些用的比较少的工具，那是得花费一番功夫。不仅要了解如何使用，为了和 `JAVACUP` 相结合，还需要了解 `JFlex` 和 `JAVACUP` 的原理，对其生成代码的结构和接口有一定认识。

实验三中，构建好翻译模式后，即可进行代码的编写。复杂度的计算颇是花费了一番功夫。一开始怎么也算不对，加之 `CUP` 的调试本身就比较麻烦，花费了很多时间在 debug 上面。计算好复杂度后，更麻烦的地方在于，语法分析和语义分析。在 `JAVACUP` 中，语法分析需要修改翻译模式，使之能够匹配错误的语法。这本身是一件比较困难的事情，但相比之下在自己编写的递归下降预测分析程序中，就没有那么麻烦。

如果正确编写了 `JAVACUP` 代码，递归下降预测分析程序是不复杂的，本质上是异曲同工的。但整个实验最困难的地方在于语义分析。语义分析需要构建符号表，而这个符号表的设计是需要花费一番功夫的。由于过程中可以声明子过程，结构体和数组也可以一直嵌套，因此设计符号表的相对困难的。

实现完成代码后，惊讶地发现，自己对原本理论知识的掌握程度又上升了一个台阶。编译原理是一个理论和实践紧密结合的学科，这句话在这次实验中得到了验证。不得不说，编译原理是很有魅力的一门学科。

这是本学期的最后一个实验，在实验过程中，老师的谆谆教诲不停地在脑海中回响，给我带来了不可磨灭的记忆。感谢老师和助教的辛勤付出。