package drintau.accountmanager.launcharg;

public class UseGUIHandler implements ArgHandlerInterface {

    @Override
    public void execute() {
        ArgsContext.getInstance().setUseGUI(true);
    }

    @Override
    public String argName() {
        return ArgsContext.ARG_NAME_USE_GUI;
    }
}
