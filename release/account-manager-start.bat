@echo off

chcp 65001
echo 正在启动account-manager...

rem 有命令行窗口，有图形化界面的启动命令
rem start java -jar account-manager.jar --useGUI
rem 有命令行窗口，无图形化界面的启动命令
rem start java -jar account-manager.jar
rem 无命令行窗口，有GUI的启动命令
start javaw -jar account-manager.jar --useGUI

rem pause