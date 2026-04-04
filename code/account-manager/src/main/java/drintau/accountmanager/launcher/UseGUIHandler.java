package drintau.accountmanager.launcher;

public class UseGUIHandler implements ArgHandlerInterface {

    @Override
    public void execute() {
        // 能执行这个方法，说明启动参数使用了 useGUI
        LauncherContext launcherContext = LauncherContext.getInstance();
        launcherContext.setUseGUI(true);
        // 标记是否为 桌面运行时
        if (launcherContext.isDesktopSupport()) {
            launcherContext.setDesktopRuntime(true);
        } else {
            launcherContext.setDesktopRuntime(false);
        }
    }

    @Override
    public String argName() {
        return ArgConstant.ARG_USE_GUI;
    }
}
