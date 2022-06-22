@echo off
cd src
cd scanner
if exist OberonScanner.java (
    del OberonScanner.java
    echo delete existed OberonScanner.java
    echo.
)
java -jar ..\..\lib\jflex-full-1.8.2.jar oberon.flex
cd ..
cd ..
pause