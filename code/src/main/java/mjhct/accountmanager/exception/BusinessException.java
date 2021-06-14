package mjhct.accountmanager.exception;

import mjhct.accountmanager.commons.CommonCode;

/**
 * 业务异常
 */
public class BusinessException extends CommonException{

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

    @Override
    public CommonCode getExceptionCode() {
        return exceptionCode;
    }

    @Override
    public void setExceptionCode(CommonCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
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
