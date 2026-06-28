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

        Font buttonFont = Font.loadFont(getClass().getClassLoader().getResourceAsStream("SourceHanSerifCN-Heavy.otf"), 20);

        // 控件
        Button startButton = new Button("启动");
        startButton.setOnAction(new WebServerStartEvent());
        startButton.setFont(buttonFont);
        desktopContext.setStartButton(startButton);

        Button stopButton = new Button("停止");
        stopButton.setOnAction(new WebServerStopEvent());
        stopButton.setDisable(true);
        stopButton.setFont(buttonFont);
        desktopContext.setStopButton(stopButton);

        Button openBrowserButton = new Button("访问");
        openBrowserButton.setOnAction(new OpenBrowserEvent());
        openBrowserButton.setDisable(true);
        openBrowserButton.setFont(buttonFont);
        desktopContext.setOpenBrowserButton(openBrowserButton);

        Button helpButton = new Button("帮助");
        helpButton.setFont(buttonFont);

        Button closeButton = new Button("关闭");
        closeButton.setFont(buttonFont);

        // 首页布局
        // 首页-顶部内容
        HBox indexTopHBox = new HBox(20);
        indexTopHBox.setPadding(new Insets(10));
        HBox.setHgrow(startButton, Priority.ALWAYS);
        HBox.setHgrow(openBrowserButton, Priority.ALWAYS);
        HBox.setHgrow(stopButton, Priority.ALWAYS);
        HBox.setHgrow(helpButton, Priority.ALWAYS);
        startButton.setMaxWidth(Double.MAX_VALUE);
        openBrowserButton.setMaxWidth(Double.MAX_VALUE);
        stopButton.setMaxWidth(Double.MAX_VALUE);
        helpButton.setMaxWidth(Double.MAX_VALUE);
        indexTopHBox.getChildren().addAll(startButton, openBrowserButton, stopButton, helpButton);

        // 首页-中间内容
        TextArea indexTextArea = new TextArea();
        desktopContext.setIndexTextArea(indexTextArea);

        indexTextArea.setEditable(false);
        indexTextArea.setWrapText(true);
        Font indexTextAreaFont = Font.loadFont(getClass().getClassLoader().getResourceAsStream("SourceHanSerifCN-Medium.otf"), 16);
        indexTextArea.setFont(indexTextAreaFont);

        HBox indexCenterHBox = new HBox();
        indexCenterHBox.setPadding(new Insets(10));
        indexCenterHBox.getChildren().addAll(indexTextArea);

        // 首页-底部内容
        Font indexLabelFont = Font.loadFont(getClass().getClassLoader().getResourceAsStream("SourceHanSerifCN-Medium.otf"), 14);
        Label versionLabel = new Label("版本号：" + launcherContext.getVersionInfo().getVersion());
        versionLabel.setFont(indexLabelFont);
        Label buildTimeLabel = new Label("构建时间：" + launcherContext.getVersionInfo().getLocalBuildTime());
        buildTimeLabel.setFont(indexLabelFont);
        HBox indexBottomHBox = new HBox(20);
        indexBottomHBox.setPadding(new Insets(10));
        indexBottomHBox.getChildren().addAll(versionLabel, buildTimeLabel);
        indexBottomHBox.setAlignment(Pos.CENTER);

        // 首页-控件分布：上中下左右
        BorderPane indexPane = new BorderPane();
        indexPane.setTop(indexTopHBox);
        indexPane.setCenter(indexCenterHBox);
        indexPane.setBottom(indexBottomHBox);

        // 帮助页布局
        Label helpLabel = new Label("""
                启动：启动WEB服务
                访问：打开本机浏览器访问WEB页面
                停止：停止WEB服务
                右上X：关闭程序，如果启用了备份功能，会进行备份
                """);
        helpLabel.setWrapText(true);
        Font helpLabelFont = Font.loadFont(getClass().getClassLoader().getResourceAsStream("SourceHanSerifCN-Medium.otf"), 18);
        helpLabel.setFont(helpLabelFont);
        HBox helpCenterHBox = new HBox();
        helpCenterHBox.setPadding(new Insets(10));
        helpCenterHBox.getChildren().addAll(helpLabel);

        HBox helpBottomHBox = new HBox(20);
        helpBottomHBox.setPadding(new Insets(10));
        HBox.setHgrow(closeButton, Priority.ALWAYS);
        closeButton.setMaxWidth(Double.MAX_VALUE);
        helpBottomHBox.getChildren().addAll(closeButton);

        BorderPane helpPane = new BorderPane();
        helpPane.setCenter(helpCenterHBox);
        helpPane.setBottom(helpBottomHBox);

        // 场景
        // 首页场景
        Scene indexScene = new Scene(indexPane);

        // 帮助页场景
        Scene helpScene = new Scene(helpPane);

        // 场景跳转
        closeButton.setOnAction(event -> {
            stage.setScene(indexScene);
        });
        helpButton.setOnAction(event -> {
            stage.setScene(helpScene);
        });

        // 窗口
        stage.setScene(indexScene);
        stage.setTitle("账号管理器");
        stage.setWidth(480);
        stage.setHeight(480);
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
                            Platform.runLater(() -> DesktopContext.getInstance().getIndexTextArea().appendText(logStr + "\n"));
                        }
                    } catch (InterruptedException ignored) {

                    }
                }, 0, 100L, TimeUnit.MILLISECONDS
        );

    }

}
