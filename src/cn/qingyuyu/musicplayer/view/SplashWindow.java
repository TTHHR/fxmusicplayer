package cn.qingyuyu.musicplayer.view;

import cn.qingyuyu.musicplayer.view.controller.SplashWindowController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class SplashWindow extends Application {
    private static int showTime=1500;
    public  void show(int showTime) {
        this.showTime=showTime;
        launch();
    }
    @Override
    public void start(Stage primaryStage) {
        try {
            // 这里的root从FXML文件中加载进行初始化，这里FXMLLoader类用于加载FXML文件
            FXMLLoader fxmlLoader=new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("SplashWindow.fxml"));
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            VBox root = fxmlLoader.load();
            Scene scene = new Scene(root, 320, 240);
            primaryStage.setScene(scene);
            primaryStage.setTitle(ResourceBundle.getBundle("string", Locale.getDefault()).getString("appName"));
            primaryStage.setResizable(false);//不可以调整大小

            SplashWindowController sc=fxmlLoader.getController();
            sc.init();
            primaryStage.getIcons().add(new Image("/assets/image/icon.png"));
            primaryStage.show();

            Thread thread = new Thread(() -> {
                try {
                    System.out.println("start"+showTime);
                    Thread.sleep(showTime);
                        Platform.runLater(primaryStage::close);
                        new MainWindow().show();
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            });
            thread.setDaemon(true);
            thread.start();


        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
