#! /bin/bash

# 有命令行窗口，无图形化界面的启动命令
java -jar account-manager.jar --am.mode=web

# 有命令行窗口，有图形化界面的启动命令
# java -jar account-manager.jar --am.mode=gui

# 无命令行窗口，有图形化界面的启动命令
# javaw -jar account-manager.jar --am.mode=gui

# 无命令行窗口，有图形化界面的启动命令，单机使用优化启动参数
# javaw -Xms96m -Xmx96m -XX:MaxMetaspaceSize=128m -XX:MetaspaceSize=64m -jar account-manager.jar --am.mode=gui
