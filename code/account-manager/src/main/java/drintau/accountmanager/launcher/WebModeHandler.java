package drintau.accountmanager.launcher;

public class WebModeHandler implements ModeHandlerInterface {

    @Override
    public void execute() {
        LauncherContext launcherContext = LauncherContext.getInstance();
        launcherContext.setWebMode(true);
        launcherContext.setGuiMode(false);
        launcherContext.setDesktopRuntime(false);
    }

    @Override
    public String modeName() {
        return ArgConstant.AM_MODE_WEB;
    }

}
