package drintau.accountmanager;

import drintau.accountmanager.desktop.DesktopContext;
import drintau.accountmanager.desktop.DesktopMainClass;
import drintau.accountmanager.launcher.ArgumentParser;
import drintau.accountmanager.launcher.LauncherContext;
import drintau.accountmanager.launcher.ModeHandlerFactory;
import drintau.accountmanager.shared.util.StrUtil;
import drintau.accountmanager.webserver.WebServerMainClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Slf4j
public class Application {

    public static void main(String[] args) {
        // 启动参数处理
        String mode = ArgumentParser.parseMode(args);
        if (StrUtil.isBlank(mode)) {
            log.error("无效的启动模式");
            System.exit(0);
        }
        new ModeHandlerFactory().create(mode).execute();

        LauncherContext launcherContext = LauncherContext.getInstance();
        launcherContext.setArgs(args);
        launcherContext.init();
        // 对应模式运行
        if (launcherContext.isDesktopRuntime()) {
            DesktopContext desktopContext = DesktopContext.getInstance();
            // .headless(false) 能使用图形化界面的情况下，springBoot也要用图形化模式
            SpringApplication springApplication = new SpringApplicationBuilder(WebServerMainClass.class).headless(false).build(args);
            desktopContext.setSpringApplication(springApplication);
            javafx.application.Application.launch(DesktopMainClass.class, args);
        } else {
            SpringApplication.run(WebServerMainClass.class, args);
        }
    }

}
