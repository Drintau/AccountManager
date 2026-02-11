package drintau.accountmanager.webserver.service.impl;

import drintau.accountmanager.commons.util.BeanUtil;
import drintau.accountmanager.webserver.dao.ConfigRepository;
import drintau.accountmanager.webserver.domain.bo.ConfigBO;
import drintau.accountmanager.webserver.domain.po.ConfigPO;
import drintau.accountmanager.webserver.service.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ConfigServiceImpl implements ConfigService {

    private final ConfigRepository configRepository;

    @Override
    public List<ConfigBO> allConfig() {
        List<ConfigPO> allConfigPOList = configRepository.findAll();
        return BeanUtil.copyList(allConfigPOList, ConfigBO.class);
    }

    @Transactional
    @Override
    public void updateConfig(List<ConfigBO> configBOList) {
        for (ConfigBO configBO : configBOList) {
            configRepository.updateConfigValueByKey(configBO.getConfigKey(), configBO.getConfigValue());
        }
    }
}
