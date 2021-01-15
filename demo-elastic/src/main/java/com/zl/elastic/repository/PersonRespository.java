package com.zl.elastic.repository;

import com.zl.elastic.model.Person;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/10/16 14:41
 */
public interface PersonRespository extends ElasticsearchRepository<Person,Long> {

    /**
     * 根据年龄区间查询
     * @param min
     * @param max
     * @return
     */
    List<Person> findByAgeBetween(Integer min, Integer max);
}
