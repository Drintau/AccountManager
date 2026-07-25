package drintau.accountmanager.shared.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import drintau.accountmanager.shared.BusinessCode;
import drintau.accountmanager.shared.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T readJsonToObj(String jsonStr, Class<T> targetClass) {
        try {
            return objectMapper.readValue(jsonStr, targetClass);
        } catch (Exception e) {
            log.error("解析json数据失败", e);
            throw new BusinessException(BusinessCode.FAIL, "解析json数据失败");
        }
    }

}
