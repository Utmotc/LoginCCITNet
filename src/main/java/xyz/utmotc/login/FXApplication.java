package xyz.utmotc.login;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import xyz.utmotc.util.DragUtil;


public class FXApplication extends Application {
    public static Stage stage;
    public static Label topLabel;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(FXApplication.class.getResource("/xyz.utmotc/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 670, 411);
        stage.setTitle("CIT自动登录");
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        DragUtil.addDragListener(stage,topLabel);

        stage.show();


    }

}
