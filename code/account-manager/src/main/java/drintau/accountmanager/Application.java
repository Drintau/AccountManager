package drintau.accountmanager;

import drintau.accountmanager.plugin.*;
import drintau.accountmanager.webserver.WebServerConfiguration;
import org.springframework.boot.SpringApplication;

import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        List<PluginInterface> startPlugins = new ArrayList<>();
        startPlugins.add(new SayHelloPlugin());
        startPlugins.add(new LaunchArgsPlugin(args));
        for (PluginInterface startPlugin : startPlugins) {
            startPlugin.execute();
        }

        /*
         * 通过JVM传参来禁用headless模式 -Djava.awt.headless=false
         */
        SpringApplication.run(WebServerConfiguration.class, args);

        List<PluginInterface> endPlugins = new ArrayList<>();
        endPlugins.add(new BackupPlugin());
        endPlugins.add(new SayByePlugin());
        for (PluginInterface endPlugin : endPlugins) {
            endPlugin.execute();
        }
    }

}
