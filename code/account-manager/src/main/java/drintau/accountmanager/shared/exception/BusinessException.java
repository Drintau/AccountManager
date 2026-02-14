package drintau.accountmanager.shared.exception;

import drintau.accountmanager.shared.BusinessCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常类
 * 用于封装业务层的异常信息，包含错误码和错误描述
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private BusinessCode errorCode;

    /**
     * 错误描述信息
     */
    private String errorMessage;

    public BusinessException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public BusinessException(BusinessCode errorCode) {
        super(errorCode.message);
        this.errorCode = errorCode;
        this.errorMessage = errorCode.message;
    }

    public BusinessException(BusinessCode errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
