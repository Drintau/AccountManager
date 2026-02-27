package drintau.accountmanager.webserver.domain.bo;

import lombok.Data;
import org.apache.fesod.sheet.annotation.ExcelProperty;
import org.apache.fesod.sheet.annotation.write.style.*;
import org.apache.fesod.sheet.enums.poi.FillPatternTypeEnum;

/**
 * 迁移：导入+导出
 * class上面的注解是导出excel时使用的样式
 */
@HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 31)
@HeadFontStyle(fontHeightInPoints = 15)
@ContentFontStyle(fontHeightInPoints = 13)
@ColumnWidth(25)
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
