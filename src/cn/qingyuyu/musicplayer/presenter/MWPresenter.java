package cn.qingyuyu.musicplayer.presenter;

import cn.qingyuyu.musicplayer.model.MusicList;
import cn.qingyuyu.musicplayer.model.MusicModel;
import cn.qingyuyu.musicplayer.view.inter.MWInterface;

import java.io.*;
import java.util.List;
import java.util.Random;

public class MWPresenter {
    private MWInterface mwi;
    private MusicPlayer mp=null;
    private MusicList ml=new MusicList();
    private int playModel=0;
    private boolean running=true;
    public MWPresenter(MWInterface mwi)
    {
        this.mwi=mwi;

        //取数据:
        File file = new File("data.dat");
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(file));
            ml = (MusicList) in.readObject();
        } catch (Exception e) {
            System.out.println("没有歌曲缓存信息");
            System.out.println(e);
        }
        mwi.setMusicList(ml);

        //设置一个线程，监听播放
        new Thread(new Runnable(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while(running)
                {
                    try{
                        Thread.sleep(500);//每隔一秒检查播放状态
                        if(mp!=null)//如果播放器不为空
                        {
                            if(mp.playerStatus!=MusicPlayer.PAUSED)
                           mwi.setPlayNowTime(mp.getPosition());
                            if(mp.playerStatus==MusicPlayer.FINISHED)//如果播放器播放完毕一首歌曲
                            {
                              nextButton(false);
                            }
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                    }


                }
            }

        }).start();

    }

    public void getImportMusic(List<File> musicList)
    {
        int index=0;
        for(;index<musicList.size();index++)
        {
            File f=musicList.get(index);
            MusicModel mm=new MusicModel(f.getName().substring(0, f.getName().lastIndexOf(".")),f.getAbsolutePath());
            ml.add(mm);
        }

        mwi.setMusicList(null);
        mwi.setMusicList(ml);
    }

    public void playButton()
    {
        if(mp==null)
        {
            if(ml.size()<=0)
            {
                mwi.setLyricText("music is null");
                return;
            }
            if(mwi.getMusicListIndex()==-1)
            {
                mwi.setMusicSelected(0);//未选中，默认选中第一个
            }
         play(mwi.getMusicListIndex());
        }
        else
        {
            if(!ml.getMusicPath(mwi.getMusicListIndex()).equals(mp.filepath)) {
                play(mwi.getMusicListIndex());
                return;
            }
            switch (mp.playerStatus)
            {
                case MusicPlayer.PAUSED:
                    mp.resume();
                    mwi.setPlayStatus(true);
                    break;
                case MusicPlayer.PLAYING:
                    mp.pause();
                    mwi.setPlayStatus(false);
                    break;
            }
        }

    }
    public void removeMusic(int index)
    {
        ml.remove(index);
        mwi.setMusicList(null);
        mwi.setMusicList(ml);
    }
    public void removeAllMusic()
    {
        ml.clear();
        mwi.setMusicList(null);
        mwi.setMusicList(ml);
    }
    public void lastButton()
    {
        int index=mwi.getMusicListIndex()-1;
        if(index<=-1)
            index=ml.size()-1;
        mwi.setMusicSelected(index);
       play(mwi.getMusicListIndex());
    }
    public void nextButton(boolean clicked)
    {
        int index=0;
        switch (playModel)
        {
            case 0:index=mwi.getMusicListIndex()+1;
                if(index>=ml.size())
                    index=0;
            break;
            case 1: index=mwi.getMusicListIndex();
                if(clicked)//按钮点击，即便在单曲循环模式，也更改音乐
            {
                index++;
                if(index>=ml.size())
                    index=0;
            }

            break;
            case 2:index=new Random().nextInt(ml.size());
            break;
        }

        mwi.setMusicSelected(index);
        play(mwi.getMusicListIndex());
    }
    public void loopButton()
    {
        playModel++;
        if(playModel>=3)
            playModel=0;
        mwi.setPlsyModel(playModel);
    }
    private void play(int index)
    {
        if(mp!=null) {
            mp.stop();
            mp.close();
        }
            mp=new MusicPlayer(ml.getMusicPath(index));
            mwi.setMusicName((String)ml.get(index));
            try {
                mp.play();
                mwi.setPlayStatus(true);
            } catch (Exception e) {
                mwi.setLyricText(e.toString());
            }
        mwi.setPlayTotalTime(mp.getTotalTime());
    }
    public void close()
    {
        if(mp!=null) {
            running=false;
            mp.close();
            System.out.println("关闭播放器");
        }
        File file = new File("data.dat");
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(ml);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("歌曲信息储存");
    }
}
