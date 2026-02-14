package drintau.accountmanager.commons.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonResult<T> implements Serializable {

    private String code;
    private String message;
    private T data;

    public CommonResult() {
        this(CommonCode.SUCCESS);
    }

    public CommonResult(T data) {
        this(CommonCode.SUCCESS, data);
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

    public CommonResult(String code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

}
