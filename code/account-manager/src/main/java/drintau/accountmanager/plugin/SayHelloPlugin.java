package drintau.accountmanager.plugin;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SayHelloPlugin implements PluginInterface{
    @Override
    public void execute() {
        log.info("欢迎使用本软件！");
    }
}
