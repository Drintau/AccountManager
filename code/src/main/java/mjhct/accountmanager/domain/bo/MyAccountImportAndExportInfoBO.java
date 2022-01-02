package mjhct.accountmanager.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 导入导出功能用到的信息
 */
public class MyAccountImportAndExportInfoBO {

    @ExcelProperty("应用名称")
    private String appName;

    @ExcelProperty("应用网址")
    private String appUrl;

    @ExcelProperty("登录名")
    private String myUsername;

    @ExcelProperty("密码")
    private String myPassword;

    @ExcelProperty("说明")
    private String remark;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getMyUsername() {
        return myUsername;
    }

    public void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }

    public String getMyPassword() {
        return myPassword;
    }

    public void setMyPassword(String myPassword) {
        this.myPassword = myPassword;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "MyAccountImportAndExportInfoBO{" +
                "appName='" + appName + '\'' +
                ", appUrl='" + appUrl + '\'' +
                ", myUsername='" + myUsername + '\'' +
                ", myPassword='" + myPassword + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
