# ALIOTH
SYSU编译原理实验5 复杂性度量工具ALIOTH

keyword: ALIOTH Oberon-0 JFlex JAVACUP


完成对 Oberon-0 的基本词法、语法、语义分析。

词法分析和语义分析基本上是完整的，语法分析报错的位置也是基本正确的，但报的错误可能会有点奇怪。

ex3 未实现语法和语义分析，如果想补充，直接将 ex4 的动作复制到 ex3 相应位置即可。

TODO LIST：
1. 可能复杂度计算有一些问题，但通过了老师所有的测试数据。有一个特别复杂的代码，涉及到过程、结构体和数组的反复嵌套：`test.13` 的计算结果应该是不对的，
2. 语法分析可以完善，目前对于缺少操作数、缺少右括号等错误还是会提示为：期望`END`。
3. ex4 代码有点丑，主要是一开始写的时候翻译模式设计的不好，可以标准化一点。
4. ex4 的调用链可以在首位加上模块，这样就可以不使用全局表了，也可以节省下面处理的时候，某一些模块一半的代码量。
5. 分析速度有点慢，主要原因在于 scanner，将文件读写部分使用StringBuffer读取会更好。
6. 感觉应该写一个总的报告。
