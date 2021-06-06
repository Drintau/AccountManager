package mjhct.accountmanager;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import mjhct.accountmanager.commons.AppLaunchArgsConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@SpringBootApplication
@ServletComponentScan
public class Application {

    public static void main(String[] args) {

        for (String arg : args) {
            if (arg.startsWith("--")) {
                String optionText = arg.substring(2);
                if (AppLaunchArgsConstant.GENERATE_AES_KEY.equals(optionText)) {
                    System.out.println("===生成AES秘钥===");
                    // hutool工具默认使用128位
                    byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
                    String aesKey = HexUtil.encodeHexStr(key, false);
                    System.out.println(aesKey);
                    // 一分钟倒计时给用户保存秘钥
                    Instant oneMinute = Instant.now().plusSeconds(60);
                    long diffAsMinutes = 60L;
                    do {
                        System.out.println("请保存秘钥，程序将在" + diffAsMinutes + "秒后退出！");
                        try {
                            // 睡眠30秒
                            Thread.sleep(30000);
                        } catch (InterruptedException e) {
                            System.out.println("程序异常退出！");
                            return;
                        }
                        diffAsMinutes = ChronoUnit.SECONDS.between(Instant.now(), oneMinute);
                    } while (diffAsMinutes > 0L);
                    System.out.println("程序退出！");
                    return;
                }
            }
        }

        SpringApplication.run(Application.class, args);

    }

}
