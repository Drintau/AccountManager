package drintau.accountmanager.launcher;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VersionInfo {

    @JsonProperty("version")
    private String version;

    @JsonProperty("build-time")
    private String buildTime;

    private String localBuildTime;

}
