package com.linus.es.demo.documents;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.*;

/**
 * @author: yuxuecheng
 * @className: Treatment
 * @ProjectName: SpringElasticRepoDemo
 * @description: TODO
 * @date: Created in 2020/2/8 21:48
 * @version: 1.0
 */
@Data
@ToString
@EqualsAndHashCode
public class Treatment {

    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_max_word"), otherFields = {@InnerField(suffix = "raw", type = FieldType.Keyword)})
    private String name;

    @Field(type = FieldType.Date, format = DateFormat.date_time_no_millis)
    private String date;

    @Field(type = FieldType.Text)
    private String perDose;

    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_max_word"), otherFields = {@InnerField(suffix = "raw", type = FieldType.Keyword)})
    private String category;
}
