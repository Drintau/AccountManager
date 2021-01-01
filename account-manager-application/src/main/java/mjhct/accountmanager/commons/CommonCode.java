package mjhct.accountmanager.commons;

public enum CommonCode {

    // 业务成功响应
    SUCCESS("000000", "业务成功"),

    // 请求参数错误
    REQUEST_PARAMETER_ERROR("000100", "请求参数错误"),

    // 加解密错误
    CRYPTO_ERROR("000200", "加解密错误"),

    // 业务失败响应
    FAIL("111111", "业务失败");

    public String code;
    public String message;

    CommonCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
