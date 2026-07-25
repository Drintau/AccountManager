package drintau.accountmanager.desktop;

import drintau.accountmanager.desktop.event.*;
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
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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

        // 字体
        Font heavy22Font = Font.loadFont(getClass().getClassLoader().getResourceAsStream("SourceHanSerifCN-Heavy.otf"), 22);
        Font heavy20Font = Font.loadFont(getClass().getClassLoader().getResourceAsStream("SourceHanSerifCN-Heavy.otf"), 20);
        Font medium18Font = Font.loadFont(getClass().getClassLoader().getResourceAsStream("SourceHanSerifCN-Medium.otf"), 18);
        Font medium16Font = Font.loadFont(getClass().getClassLoader().getResourceAsStream("SourceHanSerifCN-Medium.otf"), 16);

        // 按钮控件
        Button startButton = new Button("启动");
        startButton.setOnAction(new WebServerStartEvent());
        startButton.setFont(heavy22Font);
        startButton.setPadding(new Insets(2));
        desktopContext.setStartButton(startButton);

        Button stopButton = new Button("停止");
        stopButton.setOnAction(new WebServerStopEvent());
        stopButton.setDisable(true);
        stopButton.setFont(heavy22Font);
        stopButton.setPadding(new Insets(2));
        desktopContext.setStopButton(stopButton);

        Button openBrowserButton = new Button("访问");
        openBrowserButton.setOnAction(new OpenBrowserEvent());
        openBrowserButton.setDisable(true);
        openBrowserButton.setFont(heavy22Font);
        openBrowserButton.setPadding(new Insets(2));
        desktopContext.setOpenBrowserButton(openBrowserButton);

        Button aboutButton = new Button("关于");
        aboutButton.setFont(heavy22Font);
        aboutButton.setPadding(new Insets(2));

        Button closeButton = new Button("关闭");
        closeButton.setFont(heavy22Font);
        closeButton.setPadding(new Insets(2));

        // 首页内容
        // 首页-顶部内容
        HBox indexTopHBox = new HBox(20);
        indexTopHBox.setPadding(new Insets(10));
        HBox.setHgrow(startButton, Priority.ALWAYS);
        HBox.setHgrow(openBrowserButton, Priority.ALWAYS);
        HBox.setHgrow(stopButton, Priority.ALWAYS);
        HBox.setHgrow(aboutButton, Priority.ALWAYS);
        startButton.setMaxWidth(Double.MAX_VALUE);
        openBrowserButton.setMaxWidth(Double.MAX_VALUE);
        stopButton.setMaxWidth(Double.MAX_VALUE);
        aboutButton.setMaxWidth(Double.MAX_VALUE);
        indexTopHBox.getChildren().addAll(startButton, openBrowserButton, stopButton, aboutButton);

        // 首页-中间内容
        TextArea indexTextArea = new TextArea();
        desktopContext.setIndexTextArea(indexTextArea);

        indexTextArea.setEditable(false);
        indexTextArea.setWrapText(true);
        indexTextArea.setFont(medium18Font);

        HBox indexCenterHBox = new HBox();
        indexCenterHBox.setPadding(new Insets(10));
        indexCenterHBox.getChildren().addAll(indexTextArea);

        // 首页-底部内容
        Label versionLabel = new Label("版本号：" + launcherContext.getVersionInfo().getVersion());
        versionLabel.setFont(medium16Font);
        Label buildTimeLabel = new Label("构建时间：" + launcherContext.getVersionInfo().getLocalBuildTime());
        buildTimeLabel.setFont(medium16Font);
        HBox indexBottomHBox = new HBox(20);
        indexBottomHBox.setPadding(new Insets(10));
        indexBottomHBox.getChildren().addAll(versionLabel, buildTimeLabel);
        indexBottomHBox.setAlignment(Pos.CENTER);

        // 首页布局容器
        BorderPane indexPane = new BorderPane();
        indexPane.setTop(indexTopHBox);
        indexPane.setCenter(indexCenterHBox);
        indexPane.setBottom(indexBottomHBox);

        // 关于页内容
        // 关于页-中间内容
        Label helpTitleLabel = new Label("帮助");
        helpTitleLabel.setFont(heavy20Font);
        Label helpBodyLabel = new Label("""
                启动：启动WEB服务
                访问：打开本机浏览器访问WEB页面
                停止：停止WEB服务
                右上X：关闭程序，如果启用了备份功能，会进行备份
                """);
        helpBodyLabel.setWrapText(true);
        helpBodyLabel.setFont(medium18Font);

        Label checkTitleLabel = new Label("检查更新（需要网络）");
        checkTitleLabel.setFont(heavy20Font);
        Label currentVersionLabel = new Label("当前版本：" + launcherContext.getVersionInfo().getVersion());
        currentVersionLabel.setFont(medium18Font);
        Button checkVersionButton = new Button("查询新版");
        checkVersionButton.setFont(medium18Font);
        checkVersionButton.setPadding(new Insets(4, 8, 4, 8));
        CheckVersionEvent checkVersionEvent = new CheckVersionEvent();
        checkVersionButton.setOnAction(checkVersionEvent);
        desktopContext.setCheckVersionButton(checkVersionButton);
        Label latestVersionLabel = new Label();
        latestVersionLabel.setFont(medium18Font);
        checkVersionEvent.setLatestVersionLabel(latestVersionLabel);
        Label hasNewVersionLabel = new Label();
        hasNewVersionLabel.setFont(medium18Font);
        hasNewVersionLabel.setTextFill(Color.RED);
        checkVersionEvent.setHasNewVersionLabel(hasNewVersionLabel);

        VBox aboutCenterVBox = new VBox();
        aboutCenterVBox.setPadding(new Insets(10));
        aboutCenterVBox.getChildren().addAll(helpTitleLabel,helpBodyLabel,new Separator(),checkTitleLabel,currentVersionLabel,checkVersionButton,latestVersionLabel,hasNewVersionLabel);

        // 关于页-底部内容
        HBox aboutBottomHBox = new HBox(20);
        aboutBottomHBox.setPadding(new Insets(10));
        aboutBottomHBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(closeButton, Priority.ALWAYS);
        closeButton.setMaxWidth(Double.MAX_VALUE);
        aboutBottomHBox.getChildren().addAll(closeButton);

        // 关于页布局容器
        BorderPane aboutPane = new BorderPane();
        aboutPane.setCenter(aboutCenterVBox);
        aboutPane.setBottom(aboutBottomHBox);

        // 布局容器背景颜色设置
        Background background = Background.fill(Color.web("#2196F3", 0.1));
        indexPane.setBackground(background);
        aboutPane.setBackground(background);

        // 场景
        // 首页场景
        Scene indexScene = new Scene(indexPane);

        // 关于页场景
        Scene aboutScene = new Scene(aboutPane);

        // 场景跳转
        closeButton.setOnAction(event -> {
            stage.setScene(indexScene);
        });
        aboutButton.setOnAction(event -> {
            stage.setScene(aboutScene);
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
