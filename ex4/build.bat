@echo off
cd src
javac -d ..\bin -cp .;..\lib\complexity.jar;..\lib\exceptions.jar;..\lib\java-cup-11b.jar;..\lib\jflex-full-1.8.2.jar *.java parser\*.java scanner\*.java table\procedure\*.java table\type\*.java table\var\*.java
cd ..
pause