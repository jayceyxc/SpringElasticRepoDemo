package com.linus.es.demo.repositories;

import com.linus.es.demo.documents.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author: yuxuecheng
 * @className: BookRepository
 * @ProjectName: SpringElasticRepoDemo
 * @description: TODO
 * @date: Created in 2020/2/6 12:39
 * @version: 1.0
 */
public interface BookRepository extends ElasticsearchRepository<Book, String> {
}
