package drintau.accountmanager.shared;

import drintau.accountmanager.desktop.DesktopContext;

public class SharedContext {

    private SharedContext(){}
    private static final SharedContext instance = new SharedContext();
    public static SharedContext getInstance(){
        return instance;
    }

    public void sendLog(String logStr) {
        DesktopContext desktopContext = DesktopContext.getInstance();
        if (desktopContext.getTextArea() != null) {
            desktopContext.showLog(logStr + "\n");
        }
    }

}
