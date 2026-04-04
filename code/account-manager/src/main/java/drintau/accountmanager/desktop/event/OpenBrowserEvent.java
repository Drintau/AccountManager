package drintau.accountmanager.desktop.event;

import drintau.accountmanager.desktop.DesktopUtil;
import drintau.accountmanager.launcher.LauncherContext;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class OpenBrowserEvent implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
        DesktopUtil.openBrowser(LauncherContext.getInstance().getLocalUrl());
    }

}
