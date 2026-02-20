package drintau.accountmanager.webserver.service.impl;

import drintau.accountmanager.shared.exception.BusinessException;
import drintau.accountmanager.shared.util.BeanUtil;
import drintau.accountmanager.webserver.dao.ConfigRepository;
import drintau.accountmanager.webserver.domain.bo.ConfigBO;
import drintau.accountmanager.webserver.domain.po.ConfigPO;
import drintau.accountmanager.webserver.service.ConfigService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ConfigServiceImpl implements ConfigService {

    private final ConfigRepository configRepository;

    private List<ConfigBO> configList;

    private Map<String, String> configMap;

    @Override
    public String getConfigValue(String configKey) {
        if (MapUtils.isEmpty(configMap)) {
            allConfig();
        }

        String configValue = configMap.get(configKey);
        if (configValue != null) {
            return configValue;
        }
        return "";
    }

    @Override
    public List<ConfigBO> allConfig() {
        if (CollectionUtils.isEmpty(this.configList)) {
            List<ConfigPO> allConfigPOList = configRepository.findAll();
            List<ConfigBO> configBOList = BeanUtil.copyList(allConfigPOList, ConfigBO.class);
            setupCache(configBOList);
        }
        return this.configList;
    }

    @Transactional
    @Override
    public void updateConfig(List<ConfigBO> configBOList) {
        for (ConfigBO configBO : configBOList) {
            configRepository.updateConfigValueByKey(configBO.getConfigKey(), configBO.getConfigValue());
        }
        invalidateCache();
    }

    @Override
    public void updateConfig(ConfigBO bo) {
        configRepository.updateConfigValueByKey(bo.getConfigKey(), bo.getConfigValue());
        invalidateCache();
    }

    /**
     * 使缓存失效
     */
    private void invalidateCache() {
        this.configList = null;
        this.configMap = null;
    }

    /**
     * 设置缓存
     */
    private void setupCache(List<ConfigBO> configList) {
        if (CollectionUtils.isEmpty(configList)) {
            throw new BusinessException("配置数据设置缓存错误！");
        }

        this.configList = configList;
        this.configMap = new HashMap<>();
        for (ConfigBO configBO : this.configList) {
            this.configMap.put(configBO.getConfigKey(), configBO.getConfigValue());
        }
    }

}
