package drintau.accountmanager.desktop.event;

import drintau.accountmanager.desktop.DesktopContext;
import drintau.accountmanager.desktop.GithubReleaseInfo;
import drintau.accountmanager.shared.ThreadPool;
import drintau.accountmanager.shared.util.JsonUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
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

    @Override
    public void handle(ActionEvent event) {
        DesktopContext desktopContext = DesktopContext.getInstance();
        synchronized (this) {
            if (!desktopContext.getCheckVersionButton().isDisabled()) {
                desktopContext.getCheckVersionButton().setDisable(true);
                latestVersionLabel.setText("");
                ThreadPool.getInstance().execute(() -> {
                    try (HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build()) {
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("https://api.github.com/repos/drintau/AccountManager/releases/latest"))
                                .timeout(Duration.ofSeconds(10))
                                .GET()
                                .build();
                        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                        if (response.statusCode() != 200) {
                            Platform.runLater(() -> {
                                latestVersionLabel.setText("查询失败");
                            });
                        } else {
                            GithubReleaseInfo githubReleaseInfo = JsonUtil.readJsonToObj(response.body(), GithubReleaseInfo.class);
                            Platform.runLater(() -> {
                                latestVersionLabel.setText("最新版本：" + githubReleaseInfo.latestVersion());
                            });
                        }

                    } catch (Exception e) {
                        Platform.runLater(() -> {
                            latestVersionLabel.setText("网络异常");
                        });
                        log.error("检查新版本失败", e);
                    }

                    desktopContext.getCheckVersionButton().setDisable(false);
                });
            }
        }
    }

}
