package cn.qingyuyu.musicplayer.view.controller;

import cn.qingyuyu.musicplayer.model.MusicList;
import cn.qingyuyu.musicplayer.presenter.MWPresenter;
import cn.qingyuyu.musicplayer.view.inter.MWInterface;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements MWInterface {
    @FXML
    private Label musicName;
    @FXML
    private Label nowTime;
    @FXML
    private Label totalTime;

    @FXML
    private Slider timeSlider;

    @FXML
    private Button loopButton;
    @FXML
    private Button lastButton;
    @FXML
    private Button playButton;
    @FXML
    private Button nextButton;

    @FXML
    private TextArea lyricText;

    @FXML
    private ListView musicList;


    private MWPresenter mwp=null;

    private Stage stage;

   public void putStage(Stage stage){
        this.stage=stage;
    }

    @Override
    public void init() {
        mwp=new MWPresenter(this);
        loopButton.setGraphic(new ImageView(new Image("/assets/image/listloop.png")));
        lastButton.setGraphic(new ImageView(new Image("/assets/image/last.png")));
        playButton.setGraphic(new ImageView(new Image("/assets/image/play.png")));
        nextButton.setGraphic(new ImageView(new Image("/assets/image/next.png")));
    }

    @Override
    public void setMusicList(MusicList ml) {
        Platform.runLater(
                () -> musicList.setItems(ml)
        );

    }
    @Override
    public void setMusicName(String name) {
        Platform.runLater(
                () -> musicName.setText(name)
        );

    }
    @Override
    public void setPlayNowTime(int time) {
        Platform.runLater(
                () -> {
                    timeSlider.setValue(time / 1000);
                    int m=time/1000/60;
                    int s=time/1000%60;
                    nowTime.setText(""+m+":"+s);
                }
        );
    }

    @Override
    public void setPlayTotalTime(int time) {
        //这里time是秒
        Platform.runLater(
                () -> {
                    timeSlider.setMax(time);
                    int m=time/60;
                    int s=time%60;
                    totalTime.setText(""+m+":"+s);
                }
        );
    }

    @Override
    public int getMusicListIndex() {
        return musicList.getSelectionModel().getSelectedIndex();
    }

    @Override
    public void setPlayStatus(boolean play) {
        Platform.runLater(
                () -> {
                    if(play)
                    {
                        playButton.setGraphic(new ImageView(new Image("/assets/image/pause.png")));
                    }
                    else
                    {
                        playButton.setGraphic(new ImageView(new Image("/assets/image/play.png")));
                    }
                }
        );

    }

    @Override
    public void setMusicSelected(int index) {
        musicList.getSelectionModel().select(index);
    }

    @Override
    public void setLyricText(String text) {
        Platform.runLater(
                () -> lyricText.setText(text)
        );

    }

    @Override
    public void setPlsyModel(int index) {
        Platform.runLater(
                () -> {
                    switch (index)
                    {
                        case 0:loopButton.setGraphic(new ImageView(new Image("/assets/image/listloop.png")));
                            break;
                        case 1:loopButton.setGraphic(new ImageView(new Image("/assets/image/oneloop.png")));
                            break;
                        case 2:loopButton.setGraphic(new ImageView(new Image("/assets/image/randomloop.png")));
                    }
                }
        );

    }
    @FXML
    protected void exitMenuClick(ActionEvent event) {
       onWindowClose();
       Platform.exit();
       System.exit(0);
    }
    long lastClick=0;
    @FXML
    protected void itemClick(MouseEvent event) {
        System.out.println(event.getButton().name());
        if(event.getButton().name().equals("SECONDARY")&&getMusicListIndex()!=-1)//鼠标右键
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("remove music?");
            alert.setContentText("Choose your option.");

            ButtonType buttonTypeOne = new ButtonType("remove this music");
            ButtonType buttonTypeTwo = new ButtonType("remove all music");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne){
                    mwp.removeMusic(getMusicListIndex());
            } else if (result.get() == buttonTypeTwo) {
                    mwp.removeAllMusic();
            }
                return;
        }
        long time=System.currentTimeMillis();
        if(time-lastClick>=500)
            lastClick=time;
        else
        {
            mwp.playButton();
        }
    }
    @FXML
    protected void aboutMenuClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Info");
        alert.setHeaderText(null);
        alert.setContentText(ResourceBundle.getBundle("string", Locale.getDefault()).getString("aboutInfo"));
        alert.showAndWait();
    }
    @FXML
    protected void iMusicMenuClick(ActionEvent event)
    {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("select music");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("mp3.wav", "*.mp3","*.wav"),
                new FileChooser.ExtensionFilter("All Music", "*.*")
        );
        List<File> list =
                fileChooser.showOpenMultipleDialog(stage);
        if (list != null) {
            mwp.getImportMusic(list);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About Info");
            alert.setHeaderText(null);
            alert.setContentText("you select nothing");
            alert.showAndWait();
        }
    }
    @FXML
    protected void playButtonClick(ActionEvent event) {
       mwp.playButton();
    }
    @FXML
    protected void lastButtonClick(ActionEvent event) {
        mwp.lastButton();
    }
    @FXML
    protected void nextButtonClick(ActionEvent event) {
        mwp.nextButton(true);
    }
    @FXML
    protected void loopButtonClick(ActionEvent event) {
        mwp.loopButton();
    }

    public void onWindowClose()
    {
        mwp.close();
    }
}
