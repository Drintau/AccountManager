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

    public CommonCode getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(CommonCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String toString() {
        return "CommonException{" +
                "exceptionCode=" + exceptionCode +
                ", exceptionMessage='" + exceptionMessage + '\'' +
                '}';
    }
}
