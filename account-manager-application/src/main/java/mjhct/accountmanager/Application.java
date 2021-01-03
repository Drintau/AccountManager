package mjhct.accountmanager;

import mjhct.accountmanager.util.CryptoUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@ServletComponentScan
public class Application {

    private static final String AES_KEY_TYPE = "aesKey";

    public static void main(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("--")) {
                String optionText = arg.substring(2);
                if (AES_KEY_TYPE.equals(optionText)) {
                    System.out.println("===生成AES秘钥===");
                    String aesKey = CryptoUtil.generateAESKey();
                    System.out.println(aesKey);
                    return;
                }
            }
        }
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        // 可以进行一些额外操作
    }

}
