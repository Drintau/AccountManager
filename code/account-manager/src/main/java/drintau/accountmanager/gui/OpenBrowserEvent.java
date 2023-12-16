package drintau.accountmanager.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.springframework.beans.factory.annotation.Value;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class OpenBrowserEvent implements EventHandler<ActionEvent> {

    @Value("${server.port}")
    private String port;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public void handle(ActionEvent actionEvent) {
//        String localUrl = "http://localhost:" + port + contextPath;
        String localUrl = "http://localhost:12140/accountmanager";
        // 如果支持调用系统浏览器打开网址，则进行此操作
        if (Desktop.isDesktopSupported()) {
            URI uri = URI.create(localUrl);
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(uri);
                } catch (IOException ignored) {
                }
            }
        }
    }
}
