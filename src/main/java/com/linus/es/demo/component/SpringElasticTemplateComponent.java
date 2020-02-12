package com.linus.es.demo.component;

import com.linus.es.demo.documents.Inpatient;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: yuxuecheng
 * @className: SpringElasticTemplateComponent
 * @ProjectName: SpringElasticRepoDemo
 * @description: TODO
 * @date: Created in 2020/2/10 10:20
 * @version: 1.0
 */
@Component
@Slf4j
public class SpringElasticTemplateComponent implements CommandLineRunner {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void run(String... args) throws Exception {
//        Map<String, Object> mapping = elasticsearchRestTemplate.getIndexOperations().getMapping(Inpatient.class);
//        for (Map.Entry<String, Object> entry : mapping.entrySet()) {
//            log.info(entry.getKey() + " : " + entry.getValue());
//        }

//        Map<String, Object> inpatientMapping = elasticsearchRestTemplate.getIndexOperations().getMapping(IndexCoordinates.of("document_test"));
//        log.info("inpatient mapping: ");
//        for (Map.Entry<String, Object> entry : inpatientMapping.entrySet()) {
//            log.info(entry.getKey() + " : " + entry.getValue());
//        }
        String id = "04452e45-4c76-4cfc-9866-e705df774a80";
        GetQuery getQuery = GetQuery.getById(id);
        Inpatient inpatient = elasticsearchRestTemplate.get(getQuery, Inpatient.class, IndexCoordinates.of("qcgl_inpatient"));
        log.info(inpatient.toString());

        Query query = Query.findAll();
        query.setPageable(Pageable.unpaged().first());
        query.addSort(Sort.by("admissionDate").ascending());
    }
}
