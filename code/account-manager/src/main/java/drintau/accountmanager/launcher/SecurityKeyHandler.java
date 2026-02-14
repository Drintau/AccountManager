package drintau.accountmanager.launcher;

import drintau.accountmanager.shared.util.SecureUtil;

/**
 * 生成密钥处理程序
 */
public class SecurityKeyHandler implements ArgHandlerInterface {

    @Override
    public void execute() {
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

    @Override
    public String argName() {
        return ArgsContext.ARG_NAME_SECURITY_KEY;
    }
}
