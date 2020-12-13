package mjhct.accountmanager.exception;

import mjhct.accountmanager.commons.CommonCode;

/**
 * 公共业务异常
 */
public class BusinessException extends RuntimeException{

    /**
     * 异常码
     */
    private CommonCode exceptionCode;

    /**
     * 异常信息
     */
    private String message;

    public BusinessException(CommonCode commonCode, String message) {
        super(message);
        this.exceptionCode = commonCode;
        this.message = message;
    }

    public CommonCode getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(CommonCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BusinessException{" +
                "exceptionCode=" + exceptionCode +
                ", message='" + message + '\'' +
                '}';
    }
}
