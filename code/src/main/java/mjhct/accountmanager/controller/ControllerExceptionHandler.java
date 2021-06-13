package mjhct.accountmanager.controller;

import mjhct.accountmanager.commons.CommonCode;
import mjhct.accountmanager.commons.CommonResult;
import mjhct.accountmanager.exception.BusinessException;
import mjhct.accountmanager.exception.CommonException;
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
public class ControllerExceptionHandler {

    @ExceptionHandler(CommonException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CommonResult handleCommonException(CommonException ce) {
        return new CommonResult(ce.getExceptionCode(), ce.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CommonResult handleBusinessException(BusinessException be) {
        return new CommonResult(be.getExceptionCode(), be.getMessage());
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
        return new CommonResult(CommonCode.FAIL, e.getMessage());
    }

}
