package drintau.accountmanager.desktop;

import drintau.accountmanager.desktop.event.CloseEvent;
import drintau.accountmanager.desktop.event.OpenBrowserEvent;
import drintau.accountmanager.desktop.event.WebServerStartEvent;
import drintau.accountmanager.desktop.event.WebServerStopEvent;
import drintau.accountmanager.shared.util.DateTimeUtil;
import drintau.accountmanager.shared.util.YamlUtil;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * 主页面
 */
public class DesktopMainClass extends Application {

    @Override
    public void start(Stage stage) {
        DesktopContext desktopContext = DesktopContext.getInstance();

        // 读取配置文件的配置
        VersionInfo versionInfo = YamlUtil.readYamlToObj(getClass().getClassLoader().getResourceAsStream("application.yml"), VersionInfo.class);
        desktopContext.setVersionInfo(versionInfo);

        // 控件
        Button startButton = new Button("启动服务");
        startButton.setOnAction(new WebServerStartEvent());
        desktopContext.setStartButton(startButton);

        Button stopButton = new Button("停止服务");
        stopButton.setOnAction(new WebServerStopEvent());
        stopButton.setDisable(true);
        desktopContext.setStopButton(stopButton);

        Button openBrowserButton = new Button("访问网页");
        openBrowserButton.setOnAction(new OpenBrowserEvent());
        openBrowserButton.setDisable(true);
        desktopContext.setOpenBrowserButton(openBrowserButton);

        // 布局
        // 顶部内容
        HBox topHBox = new HBox(20);
        topHBox.setPadding(new Insets(20));
        HBox.setHgrow(startButton, Priority.ALWAYS);
        HBox.setHgrow(openBrowserButton, Priority.ALWAYS);
        HBox.setHgrow(stopButton, Priority.ALWAYS);
        startButton.setMaxWidth(Double.MAX_VALUE);
        openBrowserButton.setMaxWidth(Double.MAX_VALUE);
        stopButton.setMaxWidth(Double.MAX_VALUE);
        topHBox.getChildren().addAll(startButton, openBrowserButton, stopButton);

        // 底部内容
        Label versionLabel = new Label("版本号：" + desktopContext.getVersionInfo().getVersion());
        Label packageTimeLabel = new Label("构建时间：" + DateTimeUtil.offsetDateTimeStringToChinaZonedDateTime(desktopContext.getVersionInfo().getBuildTime()));
        HBox bottomHBox = new HBox(20);
        bottomHBox.setPadding(new Insets(10));
        bottomHBox.getChildren().addAll(versionLabel, packageTimeLabel);
        bottomHBox.setAlignment(Pos.CENTER);

        // 控件分布：上中下左右
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topHBox);
        borderPane.setBottom(bottomHBox);

        // 场景
        Scene scene = new Scene(borderPane);

        // 舞台
        stage.setScene(scene);
        stage.setTitle("账号管理器");
        stage.setWidth(450);
        stage.setHeight(150);
        stage.setResizable(false);
        stage.getIcons().add(new Image("/icon.jpg"));
        stage.setOnCloseRequest(new CloseEvent());
        stage.show();

    }

}
