package mjhct.accountmanager.exception;

import mjhct.accountmanager.commons.CommonCode;

/**
 * 公共异常
 */
public class CommonException extends RuntimeException{

    /**
     * 异常码
     */
    private CommonCode exceptionCode;

    /**
     * 异常信息
     */
    private String message;

    public CommonException(String message) {
        super(message);
    }

    public CommonException(CommonCode commonCode, String message) {
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
        return "CommonException{" +
                "exceptionCode=" + exceptionCode +
                ", message='" + message + '\'' +
                '}';
    }
}
