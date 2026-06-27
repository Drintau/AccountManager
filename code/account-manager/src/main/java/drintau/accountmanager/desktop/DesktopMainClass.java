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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
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

        Button configButton = new Button("配置");
        configButton.setFont(buttonFont);
        desktopContext.setConfigButton(configButton);

        Button saveButton = new Button("保存");
        saveButton.setFont(buttonFont);
        saveButton.setDisable(true);

        Button closeButton = new Button("关闭");
        closeButton.setFont(buttonFont);

        // 首页布局
        // 首页-顶部内容
        HBox indexTopHBox = new HBox(20);
        indexTopHBox.setPadding(new Insets(10));
        HBox.setHgrow(startButton, Priority.ALWAYS);
        HBox.setHgrow(openBrowserButton, Priority.ALWAYS);
        HBox.setHgrow(stopButton, Priority.ALWAYS);
        HBox.setHgrow(configButton, Priority.ALWAYS);
        startButton.setMaxWidth(Double.MAX_VALUE);
        openBrowserButton.setMaxWidth(Double.MAX_VALUE);
        stopButton.setMaxWidth(Double.MAX_VALUE);
        configButton.setMaxWidth(Double.MAX_VALUE);
        indexTopHBox.getChildren().addAll(startButton, openBrowserButton, stopButton, configButton);

        // 首页-中间内容
        TextArea textArea = new TextArea();
        desktopContext.setTextArea(textArea);

        textArea.setEditable(false);
        textArea.setWrapText(true);
        Font textAreaFont = Font.loadFont(getClass().getClassLoader().getResourceAsStream("SourceHanSerifCN-Medium.otf"), 16);
        textArea.setFont(textAreaFont);

        HBox indexCenterHBox = new HBox();
        indexCenterHBox.setPadding(new Insets(10));
        indexCenterHBox.getChildren().addAll(textArea);

        // 首页-底部内容
        Font labelFont = Font.loadFont(getClass().getClassLoader().getResourceAsStream("SourceHanSerifCN-Medium.otf"), 14);
        Label versionLabel = new Label("版本号：" + launcherContext.getVersionInfo().getVersion());
        versionLabel.setFont(labelFont);
        Label buildTimeLabel = new Label("构建时间：" + launcherContext.getVersionInfo().getLocalBuildTime());
        buildTimeLabel.setFont(labelFont);
        HBox indexBottomHBox = new HBox(20);
        indexBottomHBox.setPadding(new Insets(10));
        indexBottomHBox.getChildren().addAll(versionLabel, buildTimeLabel);
        indexBottomHBox.setAlignment(Pos.CENTER);

        // 首页-控件分布：上中下左右
        BorderPane indexPane = new BorderPane();
        indexPane.setTop(indexTopHBox);
        indexPane.setCenter(indexCenterHBox);
        indexPane.setBottom(indexBottomHBox);

        // 配置页布局
        List<PropertiesItem> testDataList = new ArrayList<>();
        testDataList.add(new PropertiesItem("k1", "v1"));
        testDataList.add(new PropertiesItem("k2", "v2"));

        ObservableList<PropertiesItem> observableList = FXCollections.observableList(testDataList);

        TableView<PropertiesItem> tableView = new TableView<>(observableList);
        TableColumn<PropertiesItem, String> keyColumn = new TableColumn<>("key");
        keyColumn.setCellValueFactory(cellDate -> cellDate.getValue().getKey());
        keyColumn.setSortable(false);
        keyColumn.setEditable(false);

        TableColumn<PropertiesItem, String> valueColumn = new TableColumn<>("value");
        valueColumn.setCellValueFactory(cellDate -> cellDate.getValue().getValue());
        valueColumn.setSortable(false);
        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        valueColumn.setOnEditCommit(event -> {
            event.getRowValue().setValue(event.getNewValue());
        });

        tableView.getColumns().setAll(keyColumn, valueColumn);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tableView.setEditable(true);

        HBox configCenterHBox = new HBox();
        HBox.setHgrow(tableView, Priority.ALWAYS);
        tableView.setMaxWidth(Double.MAX_VALUE);
        configCenterHBox.setPadding(new Insets(10));
        configCenterHBox.getChildren().addAll(tableView);

        HBox configBottomHBox = new HBox(20);
        configBottomHBox.setPadding(new Insets(10));
        HBox.setHgrow(closeButton, Priority.ALWAYS);
        HBox.setHgrow(saveButton, Priority.ALWAYS);
        closeButton.setMaxWidth(Double.MAX_VALUE);
        saveButton.setMaxWidth(Double.MAX_VALUE);
        configBottomHBox.getChildren().addAll(closeButton, saveButton);

        BorderPane configPane = new BorderPane();
        configPane.setCenter(configCenterHBox);
        configPane.setBottom(configBottomHBox);

        // 场景
        // 首页场景
        Scene indexScene = new Scene(indexPane);

        // 配置页场景
        Scene configScene = new Scene(configPane);

        // 场景跳转
        closeButton.setOnAction(event -> {
            stage.setScene(indexScene);
        });
        configButton.setOnAction(event -> {
            stage.setScene(configScene);
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
                            Platform.runLater(() -> DesktopContext.getInstance().getTextArea().appendText(logStr + "\n"));
                        }
                    } catch (InterruptedException ignored) {

                    }
                }, 0, 100L, TimeUnit.MILLISECONDS
        );

    }

}
