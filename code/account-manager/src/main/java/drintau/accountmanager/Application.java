package drintau.accountmanager;

import drintau.accountmanager.launcharg.ArgsContext;
import drintau.accountmanager.launcharg.ArgsHandler;
import drintau.accountmanager.gui.AMUIContext;
import drintau.accountmanager.gui.MainUI;
import drintau.accountmanager.webserver.WebServerConfiguration;
import org.springframework.boot.SpringApplication;

import java.awt.*;

public class Application {

    public static void main(String[] args) {
        // 启动参数处理
        if (args.length > 0) {
            new ArgsHandler().execute(args);
        }

        ArgsContext argsContext = ArgsContext.getInstance();
        // 用户选择使用图形化，并且操作系统支持有图形化桌面，使用图形化；否则不使用图形化
        if (argsContext.isUseGUI() && Desktop.isDesktopSupported()) {
            AMUIContext.getInstance().setArgs(args);
            javafx.application.Application.launch(MainUI.class, args);
        } else {
            SpringApplication.run(WebServerConfiguration.class, args);
        }

    }

}
