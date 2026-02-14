package drintau.accountmanager.desktop.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Maven {

    private String version;

    @JsonProperty("package-time")
    private String packageTime;

}
