package drintau.accountmanager.gui;


public class AMUIEventContainer {

    private AMUIEventContainer(){}
    private static final AMUIEventContainer instance = new AMUIEventContainer();
    public static AMUIEventContainer getInstance(){
        return instance;
    }

    private String[] args;

    private WebServerStartEvent webServerStartEvent;

    private WebServerStopEvent webServerStopEvent;

    public WebServerStartEvent getWebServerStartEvent() {
        if (webServerStartEvent == null) {
            this.webServerStartEvent = new WebServerStartEvent(args);
        }
        return webServerStartEvent;
    }

    public WebServerStopEvent getWebServerStopEvent() {
        if (webServerStopEvent == null) {
            this.webServerStopEvent = new WebServerStopEvent();
        }
        return webServerStopEvent;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
