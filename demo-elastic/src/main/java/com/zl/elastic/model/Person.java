package com.zl.elastic.model;

import com.zl.elastic.constants.EsConsts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/10/16 14:27
 */
@Document(indexName = EsConsts.INDEX_NAME,type = EsConsts.TYPE_NAME,shards = 1,replicas = 0)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String name;

    @Field(type = FieldType.Keyword)
    private String country;

    @Field(type = FieldType.Integer)
    private Integer age;

    @Field(type = FieldType.Date)
    private Date birthday;

    @Field(type = FieldType.Text,analyzer = "ik_smart")
    private String remark;
}
