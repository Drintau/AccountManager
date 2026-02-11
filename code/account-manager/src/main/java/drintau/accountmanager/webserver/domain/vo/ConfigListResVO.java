package drintau.accountmanager.webserver.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ConfigListResVO {

    @JsonProperty("list")
    private List<ConfigQueryResVO> list;

}
