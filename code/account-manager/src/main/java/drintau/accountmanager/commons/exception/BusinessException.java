package drintau.accountmanager.commons.exception;

import drintau.accountmanager.commons.domain.CommonCode;

/**
 * 业务异常
 */
public class BusinessException extends CommonException {

    public BusinessException(String businessMessage) {
        super(businessMessage);
    }

    public BusinessException(CommonCode businessCode, String businessMessage) {
        super(businessCode, businessMessage);
    }
}
