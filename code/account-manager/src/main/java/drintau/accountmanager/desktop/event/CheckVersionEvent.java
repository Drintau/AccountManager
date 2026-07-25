package drintau.accountmanager.desktop.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Slf4j
public class CheckVersionEvent implements EventHandler<ActionEvent> {

    @Setter
    private Label latestVersionLabel;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ReleaseInfo(
            @JsonProperty("name") String name
    ){}

    @Override
    public void handle(ActionEvent event) {

        String msg = "最新版本：";

        try (HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build()) {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.github.com/repos/drintau/AccountManager/releases/latest"))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                msg = "检查失败";
            }

            ReleaseInfo releaseInfo = objectMapper.readValue(response.body(), ReleaseInfo.class);

            msg = msg + releaseInfo.name();

        } catch (Exception e) {
            msg = "网络异常";
            log.error("检查新版本失败", e);
        }

        latestVersionLabel.setText(msg);
    }

}
