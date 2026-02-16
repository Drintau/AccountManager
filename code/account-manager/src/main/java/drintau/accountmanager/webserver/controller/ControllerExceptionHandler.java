package drintau.accountmanager.webserver.controller;

import drintau.accountmanager.shared.BusinessCode;
import drintau.accountmanager.webserver.CommonResult;
import drintau.accountmanager.shared.exception.BusinessException;
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

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CommonResult<Void> handleBusinessException(BusinessException be) {
        if (be.getErrorCode() == null) {
            be.setErrorCode(BusinessCode.FAIL);
        }
        return new CommonResult<>(be.getErrorCode().code, be.getErrorMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CommonResult<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        StringBuilder errMsg = new StringBuilder();
        for (ObjectError allError : allErrors) {
            errMsg.append(allError.getDefaultMessage());
            errMsg.append(";");
        }
        String errMsgStr = errMsg.toString();
        return new CommonResult<>(BusinessCode.FAIL.code, errMsgStr);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CommonResult<Void> handleOtherException(Exception e) {
        log.error("其他错误", e);
        return new CommonResult<>(BusinessCode.FAIL.code, e.getMessage());
    }

}
