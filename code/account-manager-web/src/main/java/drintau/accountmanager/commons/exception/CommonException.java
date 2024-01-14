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
public class CommonException extends RuntimeException{

    /**
     * 异常码
     */
    private CommonCode exceptionCode;

    /**
     * 自定义的异常信息
     */
    private String exceptionMessage;

    public CommonException(String message) {
        super(message);
    }

    public CommonException(CommonCode commonCode, String message) {
        super(message);
        this.exceptionCode = commonCode;
        this.exceptionMessage = message;
    }
}
