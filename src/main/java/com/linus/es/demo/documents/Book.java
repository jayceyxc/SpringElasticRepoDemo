package com.linus.es.demo.documents;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.List;

/**
 * @author: yuxuecheng
 * @className: Book
 * @ProjectName: SpringElasticRepoDemo
 * @description: TODO
 * @date: Created in 2020/2/6 12:08
 * @version: 1.0
 */
@Data
@ToString
@EqualsAndHashCode
@Document(indexName = "document_test")
public class Book {
    private @Id
    String id;

//    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    @Field(type = FieldType.Date, format = DateFormat.date_time_no_millis)
    private String createTime;

//    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_max_word")
    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_max_word"), otherFields = {@InnerField(suffix = "keyword", type = FieldType.Keyword)})
    private String name;

    @Field(type = FieldType.Double)
    private double price;

    @Field(type = FieldType.Integer)
    private int number;

    @Field(type = FieldType.Nested, name = "borrowers")
    private List<Person> borrowers;

}
