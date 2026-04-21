rem rem 是注释
rem echo off 是关闭命令回显，即不显示执行的命令，直接执行
echo off

rem 切换字符集
chcp 65001

rem 有命令行窗口，无图形化界面的启动命令
rem java -jar account-manager.jar --am.mode=web

rem 有命令行窗口，有图形化界面的启动命令
rem java -jar account-manager.jar --am.mode=gui

rem start 启动单独的“命令提示符”窗口来运行指定程序或命令。无参时将打开第二个命令提示符窗口。/b 启动应用程序时不必打开新的“命令提示符”窗口。
rem 无命令行窗口，有图形化界面的启动命令
rem start /b javaw -jar account-manager.jar --am.mode=gui

rem 无命令行窗口，有图形化界面的启动命令，限制内存使用量
start /b javaw -Xms128m -Xmx128m -jar account-manager.jar --am.mode=gui
