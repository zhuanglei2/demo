package com.zl.demo.pattern.filter.impl;

import com.zl.demo.pattern.filter.Criteria;
import com.zl.demo.pattern.filter.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/10 16:19
 */
public class CriteriaSingle implements Criteria {
    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> malePersons = new ArrayList<Person>();
        for (Person person : persons) {
            if(person.getGender().equalsIgnoreCase("SINGLE")){
                malePersons.add(person);
            }
        }
        return malePersons;
    }
}
