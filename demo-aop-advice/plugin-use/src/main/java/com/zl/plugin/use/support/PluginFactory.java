package com.zl.plugin.use.support;

import com.alibaba.fastjson.JSON;
import com.zl.plugin.use.entity.PluginConfig;
import com.zl.plugin.use.entity.PluginInfo;
import org.aopalliance.aop.Advice;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/19 16:48
 */
@Component
public class PluginFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private Map<String,PluginConfig> cachePluginConfigs = new HashMap<>();
    private Map<String,Advice> cachePlugins = new HashMap<>();

    @Value("classpath:/pluginConfig.json")
    private Resource resource;

    @PostConstruct
    public Collection<PluginConfig> initPlugin(){
        try{
            String conifg  = org.apache.commons.io.IOUtils.toString(resource.getInputStream(),Charset.forName("utf-8"));
            PluginInfo pluginInfo = JSON.parseObject(conifg, PluginInfo.class);
            for (PluginConfig pluginConfig : pluginInfo.getConfigs()) {
                cachePluginConfigs.put(pluginConfig.getId(),pluginConfig);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return cachePluginConfigs.values();
    }

    /**
     * 激活插件
     */
    public void activePlugin(String id){
        if(!cachePluginConfigs.containsKey(id)){
            throw new RuntimeException(String.format("这个插件不存在id=%s", id));
        }
        PluginConfig pluginConfig = cachePluginConfigs.get(id);
        pluginConfig.setActive(true);
        //拿到所有的beanDefinition
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        //为符合条件的bean加上插件
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);
            //bean是本类跳过
            if (bean == this) {
                continue;
            }
            //bean是null跳过
            if (bean == null) {
                continue;
            }
            //bean
            if (!(bean instanceof Advised)) { // 判断是否属于通知对象
                continue;
            }
            //判断当前bean是否有通知拦截器作用,如果有说明已经激活了直接跳过
            if(find(bean,pluginConfig.getClassName())){
                continue;
            }
            try {
                Advice advice = buildAdvice(pluginConfig);
                ((Advised) bean).addAdvice(advice);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    /**
     * 创建通知
     * @param pluginConfig
     * @return
     */
    private Advice buildAdvice(PluginConfig pluginConfig) {
        Advice advice = null;
        try{
            Boolean isLoad = null;
            //获取jar包url路径
            URL target = new URL(pluginConfig.getJarRemoteUrl());
            //获取系统类加载器
            URLClassLoader jarClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            //遍历所有jar包url比较是否已经加载过插件
            URL[] urLs = jarClassLoader.getURLs();
            for (URL urL : urLs) {
                if(target.equals(urL)){
                    System.out.println(urL+":jar包已经加载");
                    isLoad = true;
                    break;
                }
            }
            //没有加载过插件
            if(!isLoad){
                //加载jar包
                Method addUrl = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                addUrl.setAccessible(true);
                addUrl.invoke(jarClassLoader,target);
            }
            if (cachePlugins.containsKey(pluginConfig.getName())) {
                return cachePlugins.get(pluginConfig.getName());
            }
            //加载jar包中路径为pluginConfig.getClassName()的类，生成class文件
            Class<?> pluginClass = jarClassLoader.loadClass(pluginConfig.getClassName());
            //通过class生成实例对象
            advice = (Advice) pluginClass.newInstance();
            cachePlugins.put(pluginConfig.getName(), advice);

        }catch (Exception e){
            e.printStackTrace();
        }
        return advice;
    }

    /**
     * @param
     * @method remove插件
     */
    public void removePlugin(String id) {
        if (!cachePluginConfigs.containsKey(id)) {
            throw new RuntimeException(String.format("这个插件不存在id=%s", id));
        }
        //获取插件的配置信息
        PluginConfig pluginConfig = cachePluginConfigs.get(id);
        //设置激活状态
        pluginConfig.setActive(true);
        //拿到所有的beanDefinition
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        //为符合条件的bean加上插件
        for (String beanDefinition : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinition);
            if (bean == this) {
                continue;
            }
            if (bean == null) {
                continue;
            }
            if (!(bean instanceof Advised)) { // 判断是否属于通知对象
                continue;
            }
            if (find(bean, pluginConfig.getClassName())) {
                try {
                    Advice advice = buildAdvice(pluginConfig);
                    //bean加上通知
                    ((Advised) bean).removeAdvice(advice);
                } catch (Exception e) {
                    System.out.println("插件关闭失败");
                }

            }

        }

    }

    /**
     * 判断当前类是有通知拦截器的作用
     * @param bean
     * @param className
     * @return
     */
    private boolean find(Object bean, String className) {
        Advised advised = (Advised) bean;
        for (Advisor advisor : advised.getAdvisors()) {
            if(Objects.equals(className,advisor.getAdvice().getClass().getName())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
