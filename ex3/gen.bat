@echo off
cd src
if exist OberonScanner.java (
    del OberonScanner.java
    echo delete existed OberonScanner.java
    echo.
)
java -jar ..\javacup\jflex-full-1.8.2.jar oberon.flex
java -jar ..\javacup\java-cup-11b.jar -interface -parser Parser -symbols Symbols oberon.cup
cd ..
pause