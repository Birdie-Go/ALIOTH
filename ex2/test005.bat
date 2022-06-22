@echo off
@echo Running Testcase 005: IllegalIdentifierLengthException
@echo ==============================================
cd bin

java -classpath .;..\lib\complexity.jar;..\lib\exceptions.jar;..\lib\java-cup-11b.jar;..\lib\jflex-full-1.8.2.jar LexicalMain ..\src\testcase\gcd.005

cd ..
@echo ==============================================
pause
@echo on