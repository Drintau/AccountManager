package mjhct.accountmanager.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("my_account")
public class MyAccountPO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("app_name")
    private String appName;

    @TableField("app_url")
    private String appUrl;

    @TableField("my_username")
    private String myUsername;

    @TableField("my_password")
    private String myPassword;

    @TableField("remark")
    private String remark;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
