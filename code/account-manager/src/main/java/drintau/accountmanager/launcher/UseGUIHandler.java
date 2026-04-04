package drintau.accountmanager.launcher;

import java.awt.*;

public class UseGUIHandler implements ArgHandlerInterface {

    @Override
    public void execute() {
        // 能执行这个方法，说明启动参数使用了 useGUI
        // 判断是否具有桌面环境，有则参数有效，全局标记为桌面环境；否则参数无效
        if (Desktop.isDesktopSupported()) {
            LauncherContext.getInstance().setDesktopEnvironment(true);
        } else {
            LauncherContext.getInstance().setDesktopEnvironment(false);
        }
    }

    @Override
    public String argName() {
        return ArgConstant.ARG_USE_GUI;
    }
}
