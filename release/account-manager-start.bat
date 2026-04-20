@echo off

chcp 65001
echo 正在启动account-manager...

rem 有命令行窗口，无图形化界面的启动命令
rem start java -jar account-manager.jar --am.mode=web

rem 有命令行窗口，有图形化界面的启动命令
rem start java -jar account-manager.jar --am.mode=gui

rem 无命令行窗口，有图形化界面的启动命令
rem start javaw -jar account-manager.jar --am.mode=gui

rem 无命令行窗口，有图形化界面的启动命令，限制内存使用量
start javaw -Xms128m -Xmx128m -jar account-manager.jar --am.mode=gui

rem pause