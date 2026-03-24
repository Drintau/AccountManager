package drintau.accountmanager.launcher;

public class UseGUIHandler implements ArgHandlerInterface {

    @Override
    public void execute() {
        LauncherContext.getInstance().setUseGUI(true);
    }

    @Override
    public String argName() {
        return ArgsConstant.ARG_NAME_USE_GUI;
    }
}
