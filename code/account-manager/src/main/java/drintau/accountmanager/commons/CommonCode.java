package drintau.accountmanager.commons;

public enum CommonCode {

    // 业务成功响应
    SUCCESS("000000", "业务成功"),

    // 请求参数错误
    REQUEST_PARAMETER_ERROR("100000", "请求参数错误"),

    // 系统通用错误
    SYSTEM_ERROR("900000", "系统通用错误"),

    // 业务失败响应
    FAIL("999999", "业务失败");

    public String code;
    public String message;

    CommonCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
