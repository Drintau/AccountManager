package drintau.accountmanager.launcher;

import java.util.HashMap;
import java.util.Map;

public class ModeHandlerFactory {

    private final Map<String, ModeHandlerInterface> handlerMap = new HashMap<>();

    public ModeHandlerFactory() {

        KeyModeHandler keyModeHandler = new KeyModeHandler();
        handlerMap.put(keyModeHandler.modeName(), keyModeHandler);

        WebModeHandler webModeHandler = new WebModeHandler();
        handlerMap.put(webModeHandler.modeName(), webModeHandler);

        GuiModeHandler GuiModeHandler = new GuiModeHandler();
        handlerMap.put(GuiModeHandler.modeName(), GuiModeHandler);
    }

    public ModeHandlerInterface create(String modeName) {
        return handlerMap.get(modeName);
    }

}
