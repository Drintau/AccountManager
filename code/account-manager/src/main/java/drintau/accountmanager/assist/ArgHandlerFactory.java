package drintau.accountmanager.assist;

import java.util.HashMap;
import java.util.Map;

public class ArgHandlerFactory {

    private final Map<String, ArgHandlerInterface> arg2HandlerMap = new HashMap<>();

    public ArgHandlerFactory() {
        // 生成密钥处理程序并放入工厂
        GenKeyHandler genKeyHandler = new GenKeyHandler();
        arg2HandlerMap.put(genKeyHandler.argName(), genKeyHandler);
    }

    public ArgHandlerInterface create(String arg) {
        return arg2HandlerMap.get(arg);
    }

}
