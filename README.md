# account-manager
账号管理者：自己想做的小型账号密码管理软件。

### 为什么做这个
已有的一些 [密码管理软件](https://www.zhihu.com/question/27338793) 与想要的有一些差距，所以做一个自己想要的。  

### 作者的期望
1. 经济性：跨平台，免费。
2. 安全性：本地加密，秘钥、软件、密文文件三者合一才能知道明文。
3. 在线存储：云盘私有存储。
4. 自动更新：云盘同步文件夹功能，自动把密文文件更新到云盘。
5. 方便性：不想记密码；通过软件配置秘钥。  

### 软件使用方法
1. 安装配置java环境；java11以上。
2. 下载压缩包，解压到一个不带中文的目录。
3. 执行`java -jar account-manager-1.x.x.jar --securityKey`获得一个随机秘钥（windows可双击`generatekey.bat`）；把秘钥填到`application.properties`的`am.security-key=`后面。
4. 配置`spring.datasource.url=`后面的数据文件路径为本机可访问的路径。
5. 执行`java -jar account-manager-1.x.x.jar`启动程序（windows可双击`startam.bat`）。
6. 配置请参考示例配置文件`application-example.properties`。

### 存储到云盘
1. 腾讯微云同步助手有同步目录功能，修改后几秒钟就会同步。  
2. 百度网盘也有同步目录功能，修改后一分钟左右才会同步。
3. 其他网盘没有试。

### 建议
1. 配置文件多几个备份，免得配置文件坏了解不出账号密码；密文文件也可以多几个备份。
2. 配置文件和密文文件放在同一个网盘比较容易找到，虽然会稍稍增大泄密风险。
3. 只有自己一个人看密码时才可点击“高级页面”！

### 已知问题
##### 一、文件占用无法同步
###### 问题现象
1. 使用软件的同时开启网盘的自动备份，会出现密文文件占用导致网盘无法同步的情况。（原因：https://h2database.com/html/features.html#database_file_locking）
2. 步骤1现象出现后，如果本地密文文件又被修改了多次，就会出现本地与网盘数据版本相差太多而无法自动同步的情况，此时网盘和本地硬盘上会产生备份文件。
###### 解决办法
使用本软件时先不打开网盘软件（或暂时停用网盘的自动同步），关闭软件后再使用网盘的同步功能把数据更新到网盘。