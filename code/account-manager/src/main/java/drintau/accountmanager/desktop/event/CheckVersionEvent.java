package drintau.accountmanager.desktop.event;

import drintau.accountmanager.desktop.DesktopContext;
import drintau.accountmanager.desktop.GithubReleaseInfo;
import drintau.accountmanager.launcher.LauncherContext;
import drintau.accountmanager.shared.ThreadPool;
import drintau.accountmanager.shared.util.CompareUtil;
import drintau.accountmanager.shared.util.JsonUtil;
import javafx.application.Platform;
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

    @Setter
    private Label hasNewVersionLabel;

    @Override
    public void handle(ActionEvent event) {
        DesktopContext desktopContext = DesktopContext.getInstance();
        synchronized (this) {
            if (!desktopContext.getCheckVersionButton().isDisabled()) {
                desktopContext.getCheckVersionButton().setDisable(true);
                latestVersionLabel.setText("");
                hasNewVersionLabel.setText("");
                ThreadPool.getInstance().execute(() -> {
                    try (HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build()) {
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("https://api.github.com/repos/drintau/AccountManager/releases/latest"))
                                .timeout(Duration.ofSeconds(10))
                                .GET()
                                .build();
                        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                        if (response.statusCode() != 200) {
//                            log.error("查询失败：{}", response.body());
                            Platform.runLater(() -> {
                                latestVersionLabel.setText("查询失败");
                            });
                        } else {
                            GithubReleaseInfo githubReleaseInfo = JsonUtil.readJsonToObj(response.body(), GithubReleaseInfo.class);
                            String latestVersion = githubReleaseInfo.latestVersion();
                            int flag = CompareUtil.compareVersion(LauncherContext.getInstance().getVersionInfo().getVersion(), latestVersion);
                            Platform.runLater(() -> {
                                latestVersionLabel.setText("最新版本：" + githubReleaseInfo.latestVersion());
                                if (flag < 0) {
                                    hasNewVersionLabel.setText("查询到有新版，建议下载更新！");
                                }
                            });
                        }

                    } catch (Exception e) {
                        Platform.runLater(() -> {
                            latestVersionLabel.setText("网络异常");
                        });
                        log.error("网络异常", e);
                    }

                    desktopContext.getCheckVersionButton().setDisable(false);
                });
            }
        }
    }

}
