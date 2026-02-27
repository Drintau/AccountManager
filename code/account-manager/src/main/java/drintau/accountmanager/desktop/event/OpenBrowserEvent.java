package drintau.accountmanager.desktop.event;

import drintau.accountmanager.desktop.DesktopContext;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

@Slf4j
public class OpenBrowserEvent implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
        URI uri = URI.create(DesktopContext.getInstance().getLocalUrl());
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(uri);
        } catch (IOException e) {
            log.error("打开浏览器访问失败", e);
        }
    }
}
