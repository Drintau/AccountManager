package drintau.accountmanager.launcher;

import drintau.accountmanager.shared.util.SecureUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 生成密钥处理程序
 */
@Slf4j
public class SecurityKeyHandler implements ArgHandlerInterface {

    @Override
    public void execute() {
        log.info("~~~ 生成加解密秘钥 ~~~");
        try {
            log.info("密钥：{}", SecureUtil.genSecureKey());
        } catch (Exception e) {
            log.error("密钥生成出错，程序异常退出！");
            System.exit(0);
        }
        // 一分钟倒计时给用户保存秘钥
        log.info("请保存秘钥，程序将在60秒后退出！");
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            log.error("保存倒计时出错，程序异常退出！");
            System.exit(0);
        }
        log.info("程序退出！");
        System.exit(0);
    }

    @Override
    public String argName() {
        return ArgConstant.ARG_SECURITY_KEY;
    }
}
