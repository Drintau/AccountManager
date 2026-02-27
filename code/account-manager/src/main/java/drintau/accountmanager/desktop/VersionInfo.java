package drintau.accountmanager.desktop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VersionInfo {

    @JsonProperty("version")
    private String version;

    @JsonProperty("build-time")
    private String buildTime;

}
