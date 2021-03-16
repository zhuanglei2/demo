package com.zl.demo.datasource.data;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/11 17:48
 */
public class DatasourceManager {

    /**
     * 默认释放时间
     */
    private static final Long DEFAULT_RELEASE = 10L;

    @Getter
    private HikariDataSource dataSource;

    /**
     * 上一次使用时间
     */
    private LocalDateTime lastUseTime;

    public DatasourceManager(HikariDataSource dataSource){
        this.dataSource = dataSource;
        this.lastUseTime = LocalDateTime.now();
    }


    /**
     * 是否已过期,如果过期则关闭数据源
     * @return
     */
    public boolean isExpired(){
        if(LocalDateTime.now().isBefore(this.lastUseTime.plusMinutes(DEFAULT_RELEASE))){
            return false;
        }
        this.dataSource.close();
        return true;
    }

    /**
     * 刷新上次使用时间
     */
    public void refreshTime(){
        this.lastUseTime = LocalDateTime.now();
    }
}
