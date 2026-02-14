package drintau.accountmanager.launcher;

import lombok.Data;

/**
 * 启动参数环境
 */
@Data
public class ArgsContext {

    public static final String ARG_NAME_SECURITY_KEY = "securityKey";
    public static final String ARG_NAME_USE_GUI = "useGUI";

    private ArgsContext(){}
    private static final ArgsContext instance = new ArgsContext();
    public static ArgsContext getInstance(){
        return instance;
    }

    private boolean useGUI;

}
