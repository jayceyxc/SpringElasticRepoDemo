package com.linus.es.demo.documents;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author: yuxuecheng
 * @className: Person
 * @ProjectName: SpringElasticRepoDemo
 * @description: TODO
 * @date: Created in 2020/2/6 19:51
 * @version: 1.0
 */
@Data
@ToString
@EqualsAndHashCode
public class Person {

    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_max_word")
    private String name;

    @Field(type = FieldType.Keyword)
    private String sex;

    @Field(type = FieldType.Integer)
    private int age;

    @Field(type = FieldType.Date, format = DateFormat.date)
    private String birthday;
}
