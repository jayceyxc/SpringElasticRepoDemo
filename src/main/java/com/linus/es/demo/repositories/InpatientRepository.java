package com.linus.es.demo.repositories;

import com.linus.es.demo.documents.Inpatient;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author: yuxuecheng
 * @className: InpatientRepository
 * @ProjectName: SpringElasticRepoDemo
 * @description: TODO
 * @date: Created in 2020/2/8 22:15
 * @version: 1.0
 */
public interface InpatientRepository extends ElasticsearchRepository<Inpatient, String> {
}
