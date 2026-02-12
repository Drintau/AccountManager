package drintau.accountmanager.commons.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import drintau.accountmanager.commons.domain.CommonCode;

/**
 * 公共异常
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class CommonException extends RuntimeException {

    /**
     * 业务响应编码
     */
    private CommonCode businessCode;

    /**
     * 业务响应信息
     */
    private String businessMessage;

    public CommonException(String businessMessage) {
        super();
        this.businessMessage = businessMessage;
    }

    public CommonException(CommonCode businessCode, String businessMessage) {
        super();
        this.businessCode = businessCode;
        this.businessMessage = businessMessage;
    }
}
