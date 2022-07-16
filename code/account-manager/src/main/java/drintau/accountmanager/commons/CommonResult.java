package drintau.accountmanager.commons;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonResult<T> implements Serializable {

    private String code;
    private String message;
    private T data;

    public CommonResult(CommonCode commonCode, String message) {
        this.code = commonCode.code;
        this.message = message;
        this.data = null;
    }

    public CommonResult(CommonCode commonCode, String message, T data) {
        this.code = commonCode.code;
        this.message = message;
        this.data = data;
    }

    public CommonResult(CommonCode commonCode) {
        this.code = commonCode.code;
        this.message = commonCode.message;
        this.data = null;
    }

    public CommonResult(CommonCode commonCode, T data) {
        this.code = commonCode.code;
        this.message = commonCode.message;
        this.data = data;
    }

}
