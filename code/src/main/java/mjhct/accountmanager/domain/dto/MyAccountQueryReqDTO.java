package mjhct.accountmanager.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MyAccountQueryReqDTO {

    /**
     * 精确id
     */
    @JsonProperty("id")
    private Integer id;

    /**
     * 模糊应用名称
     */
    @JsonProperty("fuzzy_name")
    private String fuzzyName;

    @JsonProperty("decrypt")
    private Boolean decrypt;

    @JsonProperty("page_number")
    @Min(value = 1, message = "请求页数不能小于1")
    private Integer pageNumber;

    @JsonProperty("page_size")
    @Min(value = 1, message = "每页数据不能小于1")
    private Integer pageSize;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}
