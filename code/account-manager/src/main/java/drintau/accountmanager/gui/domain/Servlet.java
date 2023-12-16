package drintau.accountmanager.gui.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Servlet {

    @JsonProperty("context-path")
    private String contextPath;

}
