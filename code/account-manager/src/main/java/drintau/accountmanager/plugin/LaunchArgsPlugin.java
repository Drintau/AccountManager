package drintau.accountmanager.plugin;

import drintau.accountmanager.commons.AppLaunchArgsConstant;
import drintau.accountmanager.util.SecureUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LaunchArgsPlugin implements PluginInterface{

    private String[] args;

    @Override
    public void execute() {
        for (String arg : args) {
            if (arg.startsWith("--")) {
                String optionText = arg.substring(2);
                if (AppLaunchArgsConstant.GENERATE_KEY.equals(optionText)) {
                    System.out.println("===生成加解密秘钥===");
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
