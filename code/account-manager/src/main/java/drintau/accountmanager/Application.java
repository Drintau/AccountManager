package drintau.accountmanager;

import drintau.accountmanager.gui.AMUIEventContainer;
import drintau.accountmanager.gui.MainUI;
import drintau.accountmanager.plugin.*;
import drintau.accountmanager.webserver.WebServerConfiguration;
import org.springframework.boot.SpringApplication;

import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        // 如果操作系统有图形化桌面，使用图形化；否则是命令行程序
        AMUIEventContainer.getInstance().setArgs(args);
        javafx.application.Application.launch(MainUI.class, args);

//        List<PluginInterface> startPlugins = new ArrayList<>();
//        startPlugins.add(new LaunchArgsPlugin(args));
//        for (PluginInterface startPlugin : startPlugins) {
//            startPlugin.execute();
//        }
//        new LaunchArgsPlugin(args).execute();

        /*
         * 通过JVM传参来禁用headless模式 -Djava.awt.headless=false
         */
//        SpringApplication.run(WebServerConfiguration.class, args);

//        List<PluginInterface> endPlugins = new ArrayList<>();
//        endPlugins.add(new BackupPlugin());
//        for (PluginInterface endPlugin : endPlugins) {
//            endPlugin.execute();
//        }
    }

}
