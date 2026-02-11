package drintau.accountmanager.webserver.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ConfigAllResVO {

    @JsonProperty("list")
    private List<ConfigVO> list;

}
