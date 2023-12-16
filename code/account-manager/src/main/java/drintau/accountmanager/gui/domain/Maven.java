package drintau.accountmanager.gui.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Maven {

    private String version;

    @JsonProperty("package-time")
    private String packageTime;

}
