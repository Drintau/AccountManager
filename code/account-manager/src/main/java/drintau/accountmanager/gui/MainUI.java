package drintau.accountmanager.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        VBox vBox = new VBox();
        vBox.getChildren().addAll(startButton, stopButton);

        // 场景
        Scene scene = new Scene(vBox);

        // 舞台
        stage.setScene(scene);
        stage.setTitle("账号管理器");
        stage.setWidth(600);
        stage.setHeight(400);
        stage.show();
    }

}
