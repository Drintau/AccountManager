package mjhct.accountmanager.util;

import java.time.OffsetDateTime;
import java.time.ZoneId;

/**
 * 日期时间工具类
 */
public class DateTimeUtil {

    public static final ZoneId CHINA_ZONE_ID = ZoneId.of("Asia/Shanghai");

    public static final String DATETIME_PATTERN_Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";

    public static final String DATETIME_PATTERN_YMDHMS = "yyyyMMddHHmmss";

    public static final String DATE_PATTERN_Y_M_D = "yyyy-MM-dd";

    public static final String DATE_PATTERN_YMD = "yyyyMMdd";

    /**
     * 现在的北京时间
     * @return
     */
    public static OffsetDateTime nowChinaOffsetDateTime() {
        return OffsetDateTime.now(CHINA_ZONE_ID);
    }

}
