package drintau.accountmanager.webserver.service;

import drintau.accountmanager.webserver.domain.bo.ConfigBO;

import java.util.List;

public interface ConfigService {

    List<ConfigBO> allConfig();

    void updateConfig(List<ConfigBO> configBOList);

}
