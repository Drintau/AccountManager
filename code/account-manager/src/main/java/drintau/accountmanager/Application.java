package drintau.accountmanager;

import drintau.accountmanager.desktop.DesktopContext;
import drintau.accountmanager.desktop.DesktopMainClass;
import drintau.accountmanager.launcher.ArgHandlerFactory;
import drintau.accountmanager.launcher.ArgHandlerInterface;
import drintau.accountmanager.launcher.LauncherContext;
import drintau.accountmanager.webserver.WebServerMainClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.awt.*;

public class Application {

    public static void main(String[] args) {
        // 启动参数处理
        if (args.length > 0) {
            ArgHandlerFactory argHandlerFactory = new ArgHandlerFactory();
            for (String arg : args) {
                if (arg.startsWith("--")) {
                    String optionText = arg.substring(2);
                    ArgHandlerInterface argHandler = argHandlerFactory.create(optionText);
                    if (argHandler != null) {
                        argHandler.execute();
                    }
                }
            }
        }

        LauncherContext launcherContext = LauncherContext.getInstance();
        launcherContext.setArgs(args);
        launcherContext.init();
        // 用户选择使用图形化，并且操作系统支持有图形化桌面，使用图形化；否则不使用图形化
        if (launcherContext.isDesktopEnvironment()) {
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
