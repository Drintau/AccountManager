package drintau.accountmanager.plugin;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SayByePlugin implements PluginInterface{
    @Override
    public void execute() {
        log.info("感谢使用本软件，再见。");
    }
}
