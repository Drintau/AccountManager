@echo off

chcp 65001
echo 正在启动account-manager...

start java -jar account-manager-1.0.6.jar
rem 如果要启动完自动调用系统浏览器访问，就注释上面的命令，放开下面的命令
rem java -Djava.awt.headless=false -jar account-manager-1.0.7.jar

rem pause