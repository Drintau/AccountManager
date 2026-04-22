package drintau.accountmanager.shared.util;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期时间工具类
 */
public final class DateTimeUtil {

    public static final ZoneId CHINA_ZONE_ID = ZoneId.of("Asia/Shanghai");

    public static final String DATETIME_PATTERN_Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";

    public static final String DATETIME_PATTERN_Y_M_D_H_M_S_O = "yyyy-MM-dd HH:mm:ss O";

    public static final String DATETIME_PATTERN_Y_M_D_H_M_S_Z = "yyyy-MM-dd HH:mm:ss Z";

    /**
     * yyyy-MM-dd HH:mm:ss Z 格式日期 转换为 ZonedDateTime
     */
    public static OffsetDateTime offsetDateTimeStringToOffsetDateTime(String timeValue) {
        if (StrUtil.isNotBlank(timeValue)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN_Y_M_D_H_M_S_Z);
            return OffsetDateTime.parse(timeValue, dateTimeFormatter);
        }
        return null;
    }

    /**
     * 带时区的时间，转换为目标地时区时间，并进行格式化输出字符串
     */
    public static String toZoneIdDateTimeStr(OffsetDateTime offsetDateTime, ZoneId zoneId, String format) {
        if (offsetDateTime != null) {

            ZonedDateTime zonedDateTime;
            if (zoneId != null) {
                zonedDateTime = offsetDateTime.atZoneSameInstant(zoneId);
            } else {
                zonedDateTime = offsetDateTime.toZonedDateTime();
            }

            DateTimeFormatter dateTimeFormatter;
            if (StrUtil.isNotBlank(format)) {
                dateTimeFormatter = DateTimeFormatter.ofPattern(format);
            } else {
                dateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN_Y_M_D_H_M_S_Z);
            }
            return dateTimeFormatter.format(zonedDateTime);
        }
        return null;
    }

    /**
     * 带时区的时间，转换为本地时区时间，并进行格式化输出字符串
     */
    public static String toLocalDateTimeStr(OffsetDateTime offsetDateTime, String format) {
        return toZoneIdDateTimeStr(offsetDateTime, ZoneId.systemDefault(), format);
    }

    /**
     * 获取当前时间秒数
     */
    public static long getCurrentUtcSecond() {
        return Instant.now().getEpochSecond();
    }

}
