package drintau.accountmanager.util;

import org.springframework.util.StringUtils;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期时间工具类
 */
public class DateTimeUtil {

    public static final ZoneId CHINA_ZONE_ID = ZoneId.of("Asia/Shanghai");

    public static final String DATETIME_PATTERN_Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";

    public static final String DATETIME_PATTERN_Y_M_D_H_M_S_Z = "yyyy-MM-dd HH:mm:ss Z";

    public static OffsetDateTime nowChinaOffsetDateTime() {
        return OffsetDateTime.now(CHINA_ZONE_ID);
    }

    public static ZonedDateTime nowChinaZonedDateTime() {
        return ZonedDateTime.now(CHINA_ZONE_ID);
    }

    /**
     * yyyy-MM-dd HH:mm:ss Z 格式日期 转换为 ZonedDateTime，使用中国时区
     * @param timeValue
     */
    public static ZonedDateTime offsetDateTimeStringToChinaZonedDateTime(String timeValue) {
        if (StringUtils.hasText(timeValue)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN_Y_M_D_H_M_S_Z);
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(timeValue, dateTimeFormatter);
            return offsetDateTime.atZoneSameInstant(CHINA_ZONE_ID);
        }
        return null;
    }

}
