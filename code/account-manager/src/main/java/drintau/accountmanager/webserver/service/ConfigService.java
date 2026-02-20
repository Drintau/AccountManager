package drintau.accountmanager.webserver.service;

import drintau.accountmanager.webserver.domain.bo.ConfigBO;

import java.util.List;

public interface ConfigService {

    String getConfigValue(String configKey);

    List<ConfigBO> allConfig();

    void updateConfig(List<ConfigBO> configBOList);

    void updateConfig(ConfigBO bo);

}
