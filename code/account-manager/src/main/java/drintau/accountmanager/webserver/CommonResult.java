package drintau.accountmanager.webserver;

import drintau.accountmanager.shared.BusinessCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommonResult<T> implements Serializable {

    private String code;
    private String message;
    private T data;

    public CommonResult() {
        this(BusinessCode.SUCCESS);
    }

    public CommonResult(T data) {
        this(BusinessCode.SUCCESS, data);
    }

    public CommonResult(BusinessCode businessCode) {
        this.code = businessCode.code;
        this.message = businessCode.message;
        this.data = null;
    }

    public CommonResult(BusinessCode businessCode, T data) {
        this.code = businessCode.code;
        this.message = businessCode.message;
        this.data = data;
    }

    public CommonResult(String code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

}
