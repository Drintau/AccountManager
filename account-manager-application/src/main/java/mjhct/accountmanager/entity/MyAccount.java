package mjhct.accountmanager.entity;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
public class MyAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "app_name", length = 20, nullable = false)
    private String appName;

    @Column(name = "url", length = 100)
    private String url;

    @Column(name = "my_username", length = 200, nullable = false)
    private String myUsername;

    @Column(name = "my_password", length = 200, nullable = false)
    private String myPassword;

    @Column(name = "remark", length = 200)
    private String remark;

    @Column(name = "create_time")
    private OffsetDateTime createTime;

    @Column(name = "update_time")
    private OffsetDateTime updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public OffsetDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(OffsetDateTime createTime) {
        this.createTime = createTime;
    }

    public OffsetDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(OffsetDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "MyAccount{" +
                "id=" + id +
                ", appName='" + appName + '\'' +
                ", url='" + url + '\'' +
                ", myUsername='" + myUsername + '\'' +
                ", myPassword='" + myPassword + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
