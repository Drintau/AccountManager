package drintau.accountmanager.desktop;

import drintau.accountmanager.desktop.event.CloseEvent;
import drintau.accountmanager.desktop.event.OpenBrowserEvent;
import drintau.accountmanager.desktop.event.WebServerStartEvent;
import drintau.accountmanager.desktop.event.WebServerStopEvent;
import drintau.accountmanager.launcher.LauncherContext;
import drintau.accountmanager.shared.DaemonScheduler;
import drintau.accountmanager.shared.LogQueue;
import drintau.accountmanager.shared.util.StrUtil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 主界面
 */
@Slf4j
public class DesktopMainClass extends Application {

    @Override
    public void start(Stage stage) {
        LauncherContext launcherContext = LauncherContext.getInstance();
        DesktopContext desktopContext = DesktopContext.getInstance();

        Font buttonFont = new Font("System Bold", 20);

        // 控件
        Button startButton = new Button("启动服务");
        startButton.setOnAction(new WebServerStartEvent());
        startButton.setFont(buttonFont);
        desktopContext.setStartButton(startButton);

        Button stopButton = new Button("停止服务");
        stopButton.setOnAction(new WebServerStopEvent());
        stopButton.setDisable(true);
        stopButton.setFont(buttonFont);
        desktopContext.setStopButton(stopButton);

        Button openBrowserButton = new Button("访问网页");
        openBrowserButton.setOnAction(new OpenBrowserEvent());
        openBrowserButton.setDisable(true);
        openBrowserButton.setFont(buttonFont);
        desktopContext.setOpenBrowserButton(openBrowserButton);

        // 布局
        // 顶部内容
        HBox topHBox = new HBox(20);
        topHBox.setPadding(new Insets(10));
        HBox.setHgrow(startButton, Priority.ALWAYS);
        HBox.setHgrow(openBrowserButton, Priority.ALWAYS);
        HBox.setHgrow(stopButton, Priority.ALWAYS);
        startButton.setMaxWidth(Double.MAX_VALUE);
        openBrowserButton.setMaxWidth(Double.MAX_VALUE);
        stopButton.setMaxWidth(Double.MAX_VALUE);
        topHBox.getChildren().addAll(startButton, openBrowserButton, stopButton);

        // 中间内容
        TextArea textArea = new TextArea();
        desktopContext.setTextArea(textArea);

        textArea.setEditable(false);
        textArea.setWrapText(true);
        Font textAreaFont = Font.font(16);
        textArea.setFont(textAreaFont);

        HBox centerHBox = new HBox();
        centerHBox.setPadding(new Insets(10));
        centerHBox.getChildren().addAll(textArea);

        // 底部内容
        Font labelFont = Font.font(14);
        Label versionLabel = new Label("版本号：" + launcherContext.getVersionInfo().getVersion());
        versionLabel.setFont(labelFont);
        Label buildTimeLabel = new Label("构建时间：" + launcherContext.getVersionInfo().getLocalBuildTime());
        buildTimeLabel.setFont(labelFont);
        HBox bottomHBox = new HBox(20);
        bottomHBox.setPadding(new Insets(10));
        bottomHBox.getChildren().addAll(versionLabel, buildTimeLabel);
        bottomHBox.setAlignment(Pos.CENTER);

        // 控件分布：上中下左右
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topHBox);
        borderPane.setCenter(centerHBox);
        borderPane.setBottom(bottomHBox);

        // 场景
        Scene scene = new Scene(borderPane);

        // 舞台
        stage.setScene(scene);
        stage.setTitle("账号管理器");
        stage.setWidth(460);
        stage.setHeight(460);
        stage.setResizable(false);
        stage.getIcons().add(new Image("/icon.jpg"));
        stage.setOnCloseRequest(new CloseEvent());
        stage.show();

        log.info("欢迎使用账号管理器！");

        // 监听日志
        DaemonScheduler.getInstance().submitDelayTask(
                () -> {
                    try {
                        String logStr = LogQueue.getInstance().poll(5000);
                        if (StrUtil.isNotBlank(logStr)) {
                            Platform.runLater(() -> DesktopContext.getInstance().getTextArea().appendText(logStr + "\n"));
                        }
                    } catch (InterruptedException ignored) {

                    }
                }, 0, 100L, TimeUnit.MILLISECONDS
        );

    }

}
