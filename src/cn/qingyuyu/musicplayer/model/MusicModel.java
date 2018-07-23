package cn.qingyuyu.musicplayer.model;

import java.io.Serializable;

public class MusicModel implements Serializable {
    private String musicName;
    private String musicPath;
    public MusicModel(String musicName,String musicPath)
    {
        this.musicName=musicName;
        this.musicPath=musicPath;
    }
    String getMusicName() {
        return musicName;
    }

    String getMusicPath() {
        return musicPath;
    }


}
