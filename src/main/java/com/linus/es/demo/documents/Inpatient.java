package com.linus.es.demo.documents;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.List;

/**
 * @author: yuxuecheng
 * @className: Inpatient
 * @ProjectName: SpringElasticRepoDemo
 * @description: TODO
 * @date: Created in 2020/2/8 22:13
 * @version: 1.0
 */
@Data
@ToString
@EqualsAndHashCode
@Document(indexName = "qcgl_inpatient_alias", createIndex = false)
public class Inpatient {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String hospitalId;

    @Field(type = FieldType.Keyword)
    private String userId;

    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "ik_synonym_max", searchAnalyzer = "ik_synonym"), otherFields = {@InnerField(suffix = "raw", type = FieldType.Keyword)})
    private String hospitalName;

    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "ik_synonym_max", searchAnalyzer = "ik_synonym"), otherFields = {@InnerField(suffix = "raw", type = FieldType.Keyword)})
    private String payType;

    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "ik_synonym_max", searchAnalyzer = "ik_synonym"), otherFields = {@InnerField(suffix = "raw", type = FieldType.Keyword)})
    private String inpatientId;

    @Field(type = FieldType.Date, format = DateFormat.date)
    private String birthDate;

    @Field(type = FieldType.Date, format = DateFormat.date_time_no_millis)
    private String admissionDate;

    @Field(type = FieldType.Nested, name = "treatmentData")
    private List<Treatment> treatmentData;
}
