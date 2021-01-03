## account-manager
账号管理者：自己想做的小型账号密码管理软件。

#### 为什么做这个
已有的一些 [密码管理软件](https://www.zhihu.com/question/27338793) 与想要的有一些差距，所以做一个自己想要的。  
###### 想要的功能 
1. 经济性：跨平台，免费。
2. 安全性：本地加密，秘钥、软件、密文文件三者合一才能知道明文。
3. 在线存储：云盘私有存储。
4. 自动更新：云盘同步文件夹功能，自动把密文文件更新到云盘。
5. 方便性：不想记密码，软件配置秘钥，不用自己记。  

#### 软件使用方法
1. 安装配置java环境；java8以上。
2. 下载压缩包，解压到一个不带中文的目录。
3. 找个AES秘钥，也可以执行`java -jar account-manager-1.0.0.jar --aesKey`获得一个随机秘钥；把秘钥填到`application.properties`里面的`aes.key=`后面。
4. 命令行执行`java -jar account-manager-1.0.0.jar`启动程序；windows下可通过双击脚本`startam.bat`启动程序。

#### 存储到云盘
百度网盘、腾讯微云同步助手有同步目录功能；把密文文件所在的目录设置为同步目录，就会自动更新到云盘了，稍微有点不及时。  
其他网盘没有试。

#### 建议
1. 配置文件多几个备份，免得配置文件坏了解不出密码；密文文件也可以多几个备份。
2. 腾讯微云是以本地覆盖网盘，本地删了网盘也删了；百度网盘是增量，每同步一次网盘就多一个旧的备份。所以用微云就不要随便删本地文件，而且最好要有另外的备份。
3. 配置文件和密文文件放在同一个网盘比较容易找到，虽然会稍稍增大泄密风险。
4. 只有自己一个人看密码时才可点击“高级页面”！