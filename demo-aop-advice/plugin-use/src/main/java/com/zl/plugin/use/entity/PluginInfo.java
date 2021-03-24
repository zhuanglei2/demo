package com.zl.plugin.use.entity;

import java.util.List;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/19 16:53
 */
public class PluginInfo {

    private String name;
    private List<PluginConfig> configs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PluginConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<PluginConfig> configs) {
        this.configs = configs;
    }
}
