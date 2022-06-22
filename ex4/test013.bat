@echo off
cd bin
java -cp .;..\lib\complexity.jar;..\lib\exceptions.jar;..\lib\java-cup-11b.jar;..\lib\jflex-full-1.8.2.jar ParserMain ..\testcase\test.13
cd ..
pause