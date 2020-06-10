package com.zl.demo.pattern.adapter.impl;

import com.zl.demo.pattern.adapter.AdvancedMediaPlayer;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/10 14:09
 */
public class VlcPlayer implements AdvancedMediaPlayer {
    @Override
    public void playVlc(String fileName) {
        System.out.println("Playing Vlc file. name: "+ fileName);
    }

    @Override
    public void playMp4(String fileName) {

    }
}
