package cn.qingyuyu.musicplayer.view.inter;

import cn.qingyuyu.musicplayer.model.MusicList;

public interface MWInterface {
    public void setMusicList(MusicList ml);
    public void setPlayNowTime(int time);
    public void setPlayTotalTime(int time);
    public int getMusicListIndex();
    public void setPlayStatus(boolean play);
    public void setMusicSelected(int index);
    public void setLyricText(String text);
    public void setMusicName(String text);
    public void setPlsyModel(int index);
}
