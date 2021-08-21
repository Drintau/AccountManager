package mjhct.accountmanager.exception;

import mjhct.accountmanager.commons.CommonCode;

/**
 * 业务异常
 */
public class BusinessException extends CommonException{

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(CommonCode commonCode, String message) {
        super(commonCode, message);
    }
}
