package mjhct.accountmanager.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MyAccountQueryReqDTO {

    // 页码和条数是必须的
    @JsonProperty("page_number")
    @NotNull(message = "请求页码必传")
    @Min(value = 1, message = "请求页码不能小于1")
    private Integer pageNumber;

    @JsonProperty("page_size")
    @NotNull(message = "每页条数必传")
    @Min(value = 1, message = "每页条数不能小于1")
    private Integer pageSize;

    /**
     * 是否解密
     */
    @JsonProperty("decrypt")
    private Boolean decrypt = false;

    /**
     * 模糊应用名称
     */
    @JsonProperty("fuzzy_name")
    private String fuzzyName;

    public String getFuzzyName() {
        return fuzzyName;
    }

    public void setFuzzyName(String fuzzyName) {
        this.fuzzyName = fuzzyName;
    }

    public Boolean getDecrypt() {
        return decrypt;
    }

    public void setDecrypt(Boolean decrypt) {
        this.decrypt = decrypt;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "MyAccountQueryReqDTO{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", decrypt=" + decrypt +
                ", fuzzyName='" + fuzzyName + '\'' +
                '}';
    }
}
