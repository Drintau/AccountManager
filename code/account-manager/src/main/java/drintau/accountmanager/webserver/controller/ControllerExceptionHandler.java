package drintau.accountmanager.webserver.controller;

import drintau.accountmanager.commons.domain.CommonCode;
import drintau.accountmanager.commons.domain.CommonResult;
import drintau.accountmanager.commons.exception.BusinessException;
import drintau.accountmanager.commons.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(CommonException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CommonResult handleCommonException(CommonException ce) {
        if (ce.getBusinessCode() == null) {
            ce.setBusinessCode(CommonCode.FAIL);
        }
        return new CommonResult(ce.getBusinessCode(), ce.getBusinessMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CommonResult handleBusinessException(BusinessException be) {
        if (be.getBusinessCode() == null) {
            be.setBusinessCode(CommonCode.FAIL);
        }
        return new CommonResult(be.getBusinessCode(), be.getBusinessMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CommonResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        StringBuilder errMsg = new StringBuilder();
        for (ObjectError allError : allErrors) {
            errMsg.append(allError.getDefaultMessage());
            errMsg.append("；");
        }
        String errMsgStr = errMsg.substring(0, errMsg.length()-1) + "。";
        return new CommonResult(CommonCode.REQUEST_PARAMETER_ERROR, errMsgStr);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CommonResult handleOtherException(Exception e) {
        log.error("其他错误", e);
        return new CommonResult(CommonCode.FAIL, e.getMessage());
    }

}
