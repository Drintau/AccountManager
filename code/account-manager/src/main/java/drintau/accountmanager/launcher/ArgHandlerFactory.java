package drintau.accountmanager.launcher;

import java.util.HashMap;
import java.util.Map;

public class ArgHandlerFactory {

    private final Map<String, ArgHandlerInterface> arg2HandlerMap = new HashMap<>();

    public ArgHandlerFactory() {
        // 各种启动参数处理器 放入 工厂

        SecurityKeyHandler securityKeyHandler = new SecurityKeyHandler();
        arg2HandlerMap.put(securityKeyHandler.argName(), securityKeyHandler);

        UseGUIHandler useGUIHandler = new UseGUIHandler();
        arg2HandlerMap.put(useGUIHandler.argName(), useGUIHandler);
    }

    public ArgHandlerInterface create(String arg) {
        return arg2HandlerMap.get(arg);
    }

}
