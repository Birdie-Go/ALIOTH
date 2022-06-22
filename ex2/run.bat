@echo off
cd bin
java -classpath .;..\lib\complexity.jar;..\lib\exceptions.jar;..\lib\java-cup-11b.jar;..\lib\jflex-full-1.8.2.jar LexicalMain ..\src\gcd.obr
cd ..
pause
@echo on