package drintau.accountmanager.launcher;

public class GuiModeHandler implements ModeHandlerInterface {

    @Override
    public void execute() {
        LauncherContext launcherContext = LauncherContext.getInstance();
        launcherContext.setGuiMode(true);
        launcherContext.setWebMode(false);
        // 标记是否为 桌面运行时
        if (launcherContext.isDesktopSupport()) {
            launcherContext.setDesktopRuntime(true);
        } else {
            launcherContext.setDesktopRuntime(false);
        }
    }

    @Override
    public String modeName() {
        return ArgConstant.AM_MODE_GUI;
    }
}
