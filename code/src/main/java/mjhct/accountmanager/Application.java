package mjhct.accountmanager;

import mjhct.accountmanager.commons.AppLaunchArgsConstant;
import mjhct.accountmanager.util.SecureUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class Application {

    public static void main(String[] args) {

        // 不同传参处理
        handleAppLaunchArgs(args);

        /*
         * 通过JVM传参来禁用headless模式 -Djava.awt.headless=false
         */
        SpringApplication.run(Application.class, args);

        /*
         * Spring Boot 应用默认情况下运行在headless模式，无法使用AWT GUI
         * 参考解决：
         * https://my.oschina.net/hava/blog/3047377
         * https://www.cnblogs.com/huashengweilong/p/10807076.html
        通过Spring设置来禁用headless模式
        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(Application.class);
        springApplicationBuilder.headless(false).run(args);
         */
    }

    private static void handleAppLaunchArgs(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("--")) {
                String optionText = arg.substring(2);
                if (AppLaunchArgsConstant.GENERATE_AES_KEY.equals(optionText)) {
                    System.out.println("===生成AES秘钥===");
                    try {
                        System.out.println(SecureUtil.genSecureKey());
                    } catch (Exception e) {
                        System.out.println("程序异常退出！");
                        System.exit(0);
                    }
                    // 一分钟倒计时给用户保存秘钥
                    System.out.println("请保存秘钥，程序将在60秒后退出！");
                    try {
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                        System.out.println("程序异常退出！");
                        System.exit(0);
                    }
                    System.out.println("程序退出！");
                    System.exit(0);
                }
            }
        }
    }

}
