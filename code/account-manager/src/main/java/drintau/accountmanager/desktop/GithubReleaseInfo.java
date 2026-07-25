package drintau.accountmanager.desktop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GithubReleaseInfo {

    @JsonProperty("name")
    private String name;

    public String latestVersion() {
        return name.replaceFirst("^v", "");
    }

}
