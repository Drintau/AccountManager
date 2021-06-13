package mjhct.accountmanager.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MyAccountDeleteReqDTO {

    @JsonProperty("id")
    @NotNull(message = "要删除的账号id不能为空")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MyAccountDeleteReqDTO{" +
                "id=" + id +
                '}';
    }
}
