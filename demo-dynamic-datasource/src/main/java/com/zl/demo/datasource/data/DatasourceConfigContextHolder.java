package com.zl.demo.datasource.data;

/**
 * 数据源标识管理
 * @author zhuangl
 * @version 1.0
 * @date 2021/3/11 15:49
 */
public class DatasourceConfigContextHolder {
    private static final ThreadLocal<Long> DATASOURCE_HOLDER = ThreadLocal.withInitial(()->DatasourceHolder.DEFAULT_ID);

    public static void setDefaultDatasource(){
        DATASOURCE_HOLDER.remove();
    }

    public static Long getCurrentDatasourceConfig(){
        return DATASOURCE_HOLDER.get();
    }

    public static void setCurrentDatasourceConfig(Long id){
        DATASOURCE_HOLDER.set(id);
    }
}
