package drintau.accountmanager.webserver.domain.bo;

import lombok.Data;
import org.apache.fesod.sheet.annotation.ExcelProperty;

/**
 * 迁移：导入+导出
 */
@Data
public class AccountTransferBO {

    @ExcelProperty("分类")
    private String categoryName;

    @ExcelProperty("名称")
    private String appName;

    @ExcelProperty("网址")
    private String appUrl;

    @ExcelProperty("账号")
    private String username;

    @ExcelProperty("密码")
    private String password;

    @ExcelProperty("说明")
    private String remark;
}
