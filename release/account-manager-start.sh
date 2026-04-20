#! /bin/bash
echo 正在启动account-manager...

# 有命令行窗口，无图形化界面的启动命令
java -jar account-manager.jar --am.mode=web

# 有命令行窗口，有图形化界面的启动命令
# java -jar account-manager.jar --am.mode=gui

# 无命令行窗口，有图形化界面的启动命令
# javaw -jar account-manager.jar --am.mode=gui

# 无命令行窗口，有图形化界面的启动命令，限制内存使用量
# javaw -Xms128m -Xmx128m -jar account-manager.jar --am.mode=gui
