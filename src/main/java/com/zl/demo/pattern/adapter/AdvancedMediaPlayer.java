package com.zl.demo.pattern.adapter;

/**
 * 更高级的媒体播放器
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/10 14:07
 */
public interface AdvancedMediaPlayer {
    public void playVlc(String fileName);
    public void playMp4(String fileName);
}
