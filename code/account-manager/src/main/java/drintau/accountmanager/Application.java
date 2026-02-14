package drintau.accountmanager;

import drintau.accountmanager.launcher.ArgsContext;
import drintau.accountmanager.launcher.ArgsHandler;
import drintau.accountmanager.desktop.GUIContext;
import drintau.accountmanager.desktop.GUIMainClass;
import drintau.accountmanager.webserver.WebServerMainClass;
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
            GUIContext.getInstance().setArgs(args);
            javafx.application.Application.launch(GUIMainClass.class, args);
        } else {
            SpringApplication.run(WebServerMainClass.class, args);
        }

    }

}
