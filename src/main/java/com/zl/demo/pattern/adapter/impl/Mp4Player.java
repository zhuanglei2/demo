package com.zl.demo.pattern.adapter.impl;

import com.zl.demo.pattern.adapter.AdvancedMediaPlayer;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/10 14:10
 */
public class Mp4Player implements AdvancedMediaPlayer {
    @Override
    public void playVlc(String fileName) {

    }

    @Override
    public void playMp4(String fileName) {
        System.out.println("Playing mp4 file, name: "+ fileName);
    }
}
