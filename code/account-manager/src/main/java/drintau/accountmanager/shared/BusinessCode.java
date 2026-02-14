package drintau.accountmanager.shared;

public enum BusinessCode {

    // 业务成功响应
    SUCCESS("000000", "成功"),

    // 业务失败响应
    FAIL("999999", "失败");

    public final String code;
    public final String message;

    BusinessCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
