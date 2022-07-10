# account-manager
账号管理器：管理自己在网站、应用的账号密码。

### 诞生
已有的一些 [密码管理软件](https://www.zhihu.com/question/27338793) 与想要的有一些差距，所以做一个自己想要的。  

### 特点
1. 经济性：跨平台，免费。
2. 安全性：本地加密；秘钥、软件、密文文件三者合一才能知道明文。
3. 在线存储：云盘私有存储。
4. 自动更新：云盘同步文件夹功能，自动把密文文件更新到云盘。
5. 方便性：不想记密码；通过软件配置秘钥。  
6. 扩展性：自己要什么功能可以自己添加。

### 安装与使用
1. 安装配置java环境；java11以上。
2. 到 [这里](https://github.com/drintau/AccountManager/releases) 下载最新版压缩包，解压到一个不带中文的目录。
3. 复制一份参考示例配置文件`application-example.properties`，改名为`application.properties`。
4. 执行`java -jar account-manager.jar --securityKey`获得一个随机秘钥（windows可双击`generatekey.bat`）；把秘钥填到`application.properties`的`am.security-key=`后面。
5. 配置`am.file-path=`后面的数据文件路径为本机可访问的路径。
6. 执行`java -jar account-manager.jar`启动程序（windows可双击`startam.bat`）。

### 云盘同步
1. 把数据库文件放置在云盘的同步目录，即可实现自动更新到云盘。
2. 腾讯微云同步助手有同步目录功能，修改后几秒钟就会同步。  
3. 百度网盘也有同步目录功能，修改后几分钟才会同步。
4. 坚果云也有同步功能。
5. 还有其他网盘请自行尝试。

### 建议
1. 配置文件多几个备份，免得配置文件坏了解不出账号密码；密文文件也可以多几个备份。
2. 配置文件和密文文件放在同一个网盘比较容易找到。
3. 只有自己一个人看密码时才可点击“高级页面”！
4. 每次升级前，先导出现有数据做备份。
