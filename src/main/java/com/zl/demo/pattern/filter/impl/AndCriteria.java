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
public class AndCriteria implements Criteria {
    private Criteria criteria;
    private Criteria otherCriteria;

    public AndCriteria(Criteria criteria, Criteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> firstCriteriaPersons = criteria.meetCriteria(persons);
        return otherCriteria.meetCriteria(firstCriteriaPersons);
    }
}
