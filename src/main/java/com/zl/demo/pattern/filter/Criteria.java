package com.zl.demo.pattern.filter;

import java.util.List;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/6/10 16:10
 */
public interface Criteria {
    List<Person> meetCriteria(List<Person> persons);
}
