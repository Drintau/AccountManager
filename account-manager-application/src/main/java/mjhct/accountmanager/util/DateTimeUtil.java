package mjhct.accountmanager.util;

import java.time.OffsetDateTime;
import java.time.ZoneId;

public class DateTimeUtil {

    public static final ZoneId CHINA_ZONE_ID = ZoneId.of("Asia/Shanghai");

    /**
     * 现在的北京时间
     * @return
     */
    public static OffsetDateTime nowOffsetDateTime() {
        return OffsetDateTime.now(CHINA_ZONE_ID);
    }

}
