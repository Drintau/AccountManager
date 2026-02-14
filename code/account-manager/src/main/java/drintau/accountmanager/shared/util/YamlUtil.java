package drintau.accountmanager.shared.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import drintau.accountmanager.shared.BusinessCode;
import drintau.accountmanager.shared.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class YamlUtil {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T readYamlToObj(InputStream yamlFile, Class<T> targetClass) {
        try {
            return objectMapper.readValue(yamlFile, targetClass);
        } catch (IOException e) {
            log.error("", e);
            throw new BusinessException(BusinessCode.FAIL, "解析yaml文件失败");
        }
    }

}
