package cn.qingyuyu.musicplayer.view;

import cn.qingyuyu.musicplayer.view.controller.MainWindowController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Locale;
import java.util.ResourceBundle;

public class MainWindow extends Application {
    void show() {
        Platform.runLater(() -> this.start(new Stage()));
       // launch();
    }
    @Override
    public void start(Stage primaryStage) {
        try {
            // 这里的root从FXML文件中加载进行初始化，这里FXMLLoader类用于加载FXML文件
            FXMLLoader fxmlLoader=new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("MainWindow.fxml"));
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            HBox root = fxmlLoader.load();
            Scene scene = new Scene(root, 640, 480);
            primaryStage.setScene(scene);
            primaryStage.setTitle(ResourceBundle.getBundle("string", Locale.getDefault()).getString("appName"));
            primaryStage.setResizable(false);//不可以调整大小

            MainWindowController sc=fxmlLoader.getController();
            sc.putStage(primaryStage);
            sc.init();

            primaryStage.getIcons().add(new Image("/assets/image/icon.png"));

            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    sc.onWindowClose();
                    primaryStage.close();
                    System.exit(0);
                }
            });
            primaryStage.show();


        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
