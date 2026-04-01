package drintau.accountmanager.launcher;

import drintau.accountmanager.desktop.DesktopContext;
import drintau.accountmanager.shared.LogQueue;
import drintau.accountmanager.shared.ThreadPool;
import lombok.Getter;
import lombok.Setter;

/**
 * 启动器上下文，其实也是应用上下文
 */
@Getter
@Setter
public class LauncherContext {

    private LauncherContext(){}
    private static final LauncherContext instance = new LauncherContext();
    public static LauncherContext getInstance(){
        return instance;
    }

    /**
     * 启动参数
     */
    private String[] args;

    /**
     * 具备桌面环境标记
     */
    private boolean desktopEnvironment;

    /**
     * 初始化
     */
    public void init() {
        if (desktopEnvironment) {
            DesktopContext.getInstance();
            ThreadPool.getInstance();
            LogQueue.getInstance();
        }
    }

}
