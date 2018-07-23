package cn.qingyuyu.musicplayer.view.controller;

import cn.qingyuyu.musicplayer.presenter.SWPresenter;
import cn.qingyuyu.musicplayer.view.inter.SWInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.Locale;
import java.util.ResourceBundle;

public class SplashWindowController extends BaseController implements SWInterface {
        private SWPresenter swp=null;
        @FXML
        private Label copyright;
        @FXML
        private ImageView logoImage;

        @FXML
        private VBox rootBox;

        public void init()
        {
                this.swp=new SWPresenter(this);
                setCopyright(ResourceBundle.getBundle("string", Locale.getDefault()).getString("copyright"));
                setLogoImage("/assets/image/icon.png");
            // new Image(url)
            Image image = new Image("/assets/image/background.png");
// new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
            BackgroundSize backgroundSize = new BackgroundSize(320, 240, true, true, true, false);
// new BackgroundImage(image, repeatX, repeatY, position, size)
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
// new Background(images...)
            Background background = new Background(backgroundImage);
            rootBox.setBackground(background);
        }

        @Override
        public void setCopyright(String text) {
                if(copyright!=null)
                        copyright.setText(text);
        }

        @Override
        public void setLogoImage(String url) {
                if(url!=null) {
                        Image i=new Image(url,true);
                       logoImage.setImage(i);
                }
        }

}
