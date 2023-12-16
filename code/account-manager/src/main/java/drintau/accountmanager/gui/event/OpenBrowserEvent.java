package drintau.accountmanager.gui.event;

import drintau.accountmanager.gui.AMUIContext;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.springframework.beans.factory.annotation.Value;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class OpenBrowserEvent implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
        AMUIContext amuiContext = AMUIContext.getInstance();
        String localUrl = "http://localhost:"
                + amuiContext.getConfigValue().getServer().getPort()
                + amuiContext.getConfigValue().getServer().getServlet().getContextPath();
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
