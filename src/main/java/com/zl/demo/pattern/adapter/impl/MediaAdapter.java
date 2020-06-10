package com.zl.demo.pattern.adapter.impl;

import com.zl.demo.pattern.adapter.AdvancedMediaPlayer;
import com.zl.demo.pattern.adapter.MediaPlayer;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/10 14:11
 */
public class MediaAdapter implements MediaPlayer {

    AdvancedMediaPlayer advancedMediaPlayer;
    public MediaAdapter (String audioType){
        if(audioType.equalsIgnoreCase("vlc")){
            advancedMediaPlayer = new VlcPlayer();
        }else if (audioType.equalsIgnoreCase("mp4")){
            advancedMediaPlayer = new Mp4Player();
        }
    }

    @Override
    public void play(String audioType, String fileName) {
        if(audioType.equalsIgnoreCase("vlc")){
            advancedMediaPlayer.playVlc(fileName);
        }else if (audioType.equalsIgnoreCase("mp4")){
            advancedMediaPlayer.playMp4(fileName);
        }
    }
}
