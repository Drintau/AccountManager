package drintau.accountmanager.launcher;

import lombok.Data;

/**
 * 启动上下文，其实也是应用上下文
 */
@Data
public class LauncherContext {

    private LauncherContext(){}
    private static final LauncherContext instance = new LauncherContext();
    public static LauncherContext getInstance(){
        return instance;
    }

    private boolean useGUI;

}
