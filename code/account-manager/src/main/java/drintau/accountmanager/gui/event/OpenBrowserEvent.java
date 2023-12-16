package drintau.accountmanager.gui.event;

import drintau.accountmanager.gui.AMUIContext;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

@Slf4j
public class OpenBrowserEvent implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
        URI uri = URI.create(AMUIContext.getInstance().getLocalUrl());
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(uri);
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
