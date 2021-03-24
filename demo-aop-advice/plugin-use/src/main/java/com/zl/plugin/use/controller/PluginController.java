package com.zl.plugin.use.controller;

import com.zl.plugin.use.support.PluginFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/19 16:48
 */
@RestController
public class PluginController {
    @Autowired
    private PluginFactory pluginFactory;


    @RequestMapping("/test/{id}")
    public String test(@PathVariable("id") String id) {
        return "testsuccess";
    }
    @RequestMapping("/plugin/active/{id}")
    public String testadd(@PathVariable("id") String id) {
        pluginFactory.activePlugin(id);
        return "activesuccess";
    }
    @RequestMapping("/plugin/remove/{id}")
    public String testremove(@PathVariable("id") String id) {
        pluginFactory.removePlugin(id);
        return "removesuccess";
    }

}
