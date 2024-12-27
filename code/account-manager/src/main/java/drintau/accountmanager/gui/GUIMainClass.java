package drintau.accountmanager.gui;

import drintau.accountmanager.commons.util.DateTimeUtil;
import drintau.accountmanager.commons.util.YamlUtil;
import drintau.accountmanager.gui.domain.ConfigValue;
import drintau.accountmanager.gui.event.CloseEvent;
import drintau.accountmanager.gui.event.OpenBrowserEvent;
import drintau.accountmanager.gui.event.WebServerStartEvent;
import drintau.accountmanager.gui.event.WebServerStopEvent;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * 主页面
 */
public class GUIMainClass extends Application {

    @Override
    public void start(Stage stage) {
        GUIContext guiContext = GUIContext.getInstance();

        // 读取配置文件的配置
        ConfigValue configValue = YamlUtil.readYamlToObj(getClass().getClassLoader().getResourceAsStream("application.yml"), ConfigValue.class);
        guiContext.setConfigValue(configValue);

        // 控件
        Button startButton = new Button("启动服务");
        startButton.setOnAction(new WebServerStartEvent());
        guiContext.setStartButton(startButton);

        Button stopButton = new Button("停止服务");
        stopButton.setOnAction(new WebServerStopEvent());
        stopButton.setDisable(true);
        guiContext.setStopButton(stopButton);

        Button openBrowserButton = new Button("打开浏览器访问");
        openBrowserButton.setOnAction(new OpenBrowserEvent());
        openBrowserButton.setDisable(true);
        guiContext.setOpenBrowserButton(openBrowserButton);

        // 布局
        // 顶部内容
        HBox topHBox = new HBox(20);
        topHBox.setPadding(new Insets(10,10,10,10));
        topHBox.getChildren().addAll(startButton, openBrowserButton, stopButton);

        // 中部内容
        TextArea outputTextArea = new TextArea();
        outputTextArea.setEditable(false);
        guiContext.setOutputTextArea(outputTextArea);
        HBox centerHBox = new HBox();
        centerHBox.setPadding(new Insets(10,10,10,10));
        centerHBox.getChildren().addAll(outputTextArea);

        // 底部内容
        Label versionLabel = new Label("版本号：" +
                guiContext.getConfigValue().getMaven().getVersion());
        Label packageTimeLabel = new Label("构建时间：" +
                DateTimeUtil.offsetDateTimeStringToChinaZonedDateTime(guiContext.getConfigValue().getMaven().getPackageTime()));
        HBox bottomHBox = new HBox(20);
        bottomHBox.setPadding(new Insets(10,10,10,10));
        bottomHBox.getChildren().addAll(versionLabel, packageTimeLabel);
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
        stage.setWidth(600);
        stage.setHeight(400);
        stage.setResizable(false);
        stage.getIcons().add(new Image("/icon.jpg"));
        stage.setOnCloseRequest(new CloseEvent());
        stage.show();

    }

}
