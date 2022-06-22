@echo off
cd bin
java -cp .;..\javacup\complexity.jar;..\javacup\exceptions.jar;..\javacup\java-cup-11b.jar;..\javacup\jflex-full-1.8.2.jar ParserMain ..\testcase\gcd.007
cd ..
pause