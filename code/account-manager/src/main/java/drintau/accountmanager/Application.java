package drintau.accountmanager;

import drintau.accountmanager.assist.LaunchArgsHandler;
import drintau.accountmanager.gui.AMUIContext;
import drintau.accountmanager.gui.MainUI;
import drintau.accountmanager.webserver.WebServerConfiguration;
import org.springframework.boot.SpringApplication;

import java.awt.*;

public class Application {

    public static void main(String[] args) {
        // 额外启动参数处理
        if (args.length > 0) {
            new LaunchArgsHandler().execute(args);
        }

        // 如果操作系统有图形化桌面，使用图形化；否则不使用图形化
        if (Desktop.isDesktopSupported()) {
            AMUIContext.getInstance().setArgs(args);
            javafx.application.Application.launch(MainUI.class, args);
        } else {
            SpringApplication.run(WebServerConfiguration.class, args);
        }
    }

}
