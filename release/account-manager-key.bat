@echo off

chcp 65001
echo 正在生成秘钥...

start java -jar account-manager.jar --securityKey

rem pause