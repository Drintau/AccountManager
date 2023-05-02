package drintau.accountmanager.webserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class WebServerConfiguration {

    /**
     * Spring Boot 应用默认情况下运行在headless模式，无法使用AWT GUI
     * 参考解决：
     * https://my.oschina.net/hava/blog/3047377
     * https://www.cnblogs.com/huashengweilong/p/10807076.html
     */
}
