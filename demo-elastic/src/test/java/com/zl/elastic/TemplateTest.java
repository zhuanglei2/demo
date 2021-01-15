package com.zl.elastic;

import com.zl.elastic.model.Person;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/10/16 14:43
 */
public class TemplateTest extends DemoElasticApplicationTests {

    @Autowired
    private ElasticsearchRestTemplate esTemplate;


    @Test
    public void testCreateIndex(){
        // 创建索引，会根据Item类的@Document注解信息来创建
        esTemplate.createIndex(Person.class);

        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
        esTemplate.putMapping(Person.class);
    }

    @Test
    public void testDeleteIndex(){
        esTemplate.deleteIndex(Person.class);
    }
}
