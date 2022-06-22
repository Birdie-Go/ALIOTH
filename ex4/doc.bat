@echo off
cd src
javadoc -private -author -version -d ..\doc -classpath .;..\lib\complexity.jar;..\lib\exceptions.jar;..\lib\java-cup-11b.jar;..\lib\jflex-full-1.8.2.jar *.java parser\*.java scanner\*.java table\procedure\*.java table\type\*.java table\var\*.java
cd ..
pause
@echo on
