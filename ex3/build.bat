@echo off
cd src
javac -d ..\bin -cp .;..\javacup\complexity.jar;..\javacup\exceptions.jar;..\javacup\java-cup-11b.jar;..\javacup\jflex-full-1.8.2.jar *.java
cd ..
pause