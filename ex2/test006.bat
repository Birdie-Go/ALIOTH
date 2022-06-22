@echo off
@echo Running Testcase 006: MismatchedCommentException
@echo ==============================================
cd bin

java -classpath .;..\lib\complexity.jar;..\lib\exceptions.jar;..\lib\java-cup-11b.jar;..\lib\jflex-full-1.8.2.jar LexicalMain ..\src\testcase\gcd.006

cd ..
@echo ==============================================
pause
@echo on