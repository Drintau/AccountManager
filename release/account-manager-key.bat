rem rem 是注释
rem echo off 是关闭命令回显，即不显示执行的命令，直接执行
echo off

rem 切换字符集
chcp 65001

java -jar account-manager.jar --am.mode=key
