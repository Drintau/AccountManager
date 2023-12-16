package drintau.accountmanager.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 主页面
 */
public class MainUI extends Application {

    @Override
    public void start(Stage stage) {
        AMUIContext amuiContext = AMUIContext.getInstance();

        // 控件
        Button startButton = new Button("启动");
        startButton.setOnAction(new WebServerStartEvent());
        amuiContext.setStartButton(startButton);

        Button stopButton = new Button("停止");
        stopButton.setOnAction(new WebServerStopEvent());
        stopButton.setDisable(true);
        amuiContext.setStopButton(stopButton);

        // 布局
        // 控件分布：一行
        HBox topHBox = new HBox(20);
        topHBox.setPadding(new Insets(10,10,10,10));
        topHBox.getChildren().addAll(startButton, stopButton);
        // 控件分布：上中下左右
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topHBox);

        // 场景
        Scene scene = new Scene(borderPane);

        // 舞台
        stage.setScene(scene);
        stage.setTitle("账号管理器");
        stage.setWidth(600);
        stage.setHeight(400);
        stage.setResizable(false);
        stage.setOnCloseRequest(new CloseEvent());
        stage.show();
    }

}
