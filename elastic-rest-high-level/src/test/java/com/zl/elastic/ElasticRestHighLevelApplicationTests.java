package com.zl.elastic;

import com.zl.elastic.constant.ElasticsearchConstant;
import com.zl.elastic.model.Person;
import com.zl.elastic.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticRestHighLevelApplicationTests {

    @Autowired
    private PersonService personService;

    @Test
    public void deleteIndexTest(){
        personService.deleteIndex(ElasticsearchConstant.INDEX_NAME);
    }

    @Test
    public void createIndexTest(){
        personService.createIndex(ElasticsearchConstant.INDEX_NAME);
    }

    @Test
    public void insertTest(){
        List<Person> list = new ArrayList<>();
        list.add(Person.builder().age(11).birthday(new Date()).country("China").id(1L).name("善诊").remark("test1").build());
        list.add(Person.builder().age(22).birthday(new Date()).country("US").id(2L).name("善诊2").remark("test2").build());
        list.add(Person.builder().age(33).birthday(new Date()).country("China").id(3L).name("善诊3").remark("test3").build());

        personService.insert(ElasticsearchConstant.INDEX_NAME,list);
    }

    @Test
    public void searchListTest(){
        List<Person> personList = personService.searchList(ElasticsearchConstant.INDEX_NAME);
        System.out.println(personList);
    }

    @Test
    public void updateTest(){
        Person person = Person.builder().age(33).birthday(new Date()).country("ID_UPDATE").id(3L).name("善诊3").remark("更新").build();
        List<Person> list = new ArrayList<>();
        list.add(person);
        personService.update(ElasticsearchConstant.INDEX_NAME,list);
    }
}
