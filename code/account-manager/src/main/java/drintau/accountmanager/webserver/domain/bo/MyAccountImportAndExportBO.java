package drintau.accountmanager.webserver.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 导入导出功能用到的信息
 */
@Data
public class MyAccountImportAndExportBO {

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
}
