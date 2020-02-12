package com.linus.es.demo.component;

import com.linus.es.demo.documents.Book;
import com.linus.es.demo.documents.Inpatient;
import com.linus.es.demo.documents.Person;
import com.linus.es.demo.repositories.BookRepository;
import com.linus.es.demo.repositories.InpatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.ShardSearchFailure;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.TypeVariable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: yuxuecheng
 * @className: SpringElasticTest
 * @ProjectName: demo
 * @description: TODO
 * @date: Created in 2020/2/6 10:22
 * @version: 1.0
 */
@Component
@Slf4j
public class SpringElasticTestComponent implements CommandLineRunner {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    InpatientRepository inpatientRepository;

    private void saveBook() {
        SimpleDateFormat targetDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+0800");
        SimpleDateFormat birthdayDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Book book = new Book();
        book.setId(UUID.randomUUID().toString());
        book.setCreateTime(targetDateFormat.format(Calendar.getInstance().getTime()));
        book.setName("Java");
        book.setPrice(16.7);
        book.setNumber(10);
        List<Person> personList = new ArrayList<>();
        Person bill = new Person();
        bill.setName("Bill");
        bill.setAge(26);
        bill.setSex("Male");
        Calendar birthday = Calendar.getInstance();
        birthday.set(1987, 8, 7);
        bill.setBirthday(birthdayDateFormat.format(birthday.getTime()));
        personList.add(bill);
        Person jason = new Person();
        jason.setName("Jason");
        jason.setAge(46);
        jason.setSex("Female");
        birthday.set(1977, 8, 7);
        jason.setBirthday(birthdayDateFormat.format(birthday.getTime()));
        personList.add(jason);
        book.setBorrowers(personList);
        Book savedBook = bookRepository.save(book);
        log.info(savedBook.toString());
    }

    private void readBook() {
        Iterable<Book> bookIterable = bookRepository.findAll();
        bookIterable.forEach(book -> {
            log.info(book.getName());
            log.info(book.toString());
        });
    }

    private void readInpatient() {
//        Iterable<Inpatient> inpatientIterable = inpatientRepository.findAll();
        Optional<Inpatient> optionalInpatient = inpatientRepository.findById("04452e45-4c76-4cfc-9866-e705df774a80");
        Inpatient inpatient = optionalInpatient.orElse(null);
        log.info(inpatient != null ? inpatient.getHospitalName() : "");
//        inpatientIterable.forEach(new Consumer<Inpatient>() {
//            @Override
//            public void accept(Inpatient inpatient) {
//                log.info(inpatient.getId());
//                log.info(inpatient.getHospitalId());
//                log.info(inpatient.getHospitalName());
//                log.info(inpatient.getTreatmentData().toString());
//            }
//        });
    }

    private void readObject(Class clazz) {
        try {
            PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(clazz);
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                log.info(propertyDescriptor.getPropertyType().getCanonicalName());
                log.info(propertyDescriptor.toString());
                TypeVariable<?>[] typeVariables = propertyDescriptor.getPropertyType().getTypeParameters();
                for (int i = 0; i < typeVariables.length; ++i) {
                    log.info(typeVariables[i].getName());
                    log.info(typeVariables[i].getTypeName());
                }
            }
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices("qcgl_inpatient_alias");
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            long tookInMillis = searchResponse.getTook().getMillis();

            int failedShards = searchResponse.getFailedShards();
            if (failedShards > 0) {
                log.error("搜索时有shards失败");
                ShardSearchFailure[] failures = searchResponse.getShardFailures();
                for (ShardSearchFailure failure : failures) {
                    log.error("失败索引名称：" + failure.index() + "，失败原因：" + failure.reason(), failure.getCause());
                }
            }
            log.info(searchHits.toString());
            SearchHit[] hits = searchHits.getHits();
            for (SearchHit hit : hits) {
                Map<String, DocumentField> documentFieldMap = hit.getFields();
                Map<String, Object> sourceMap = hit.getSourceAsMap();
                log.info(sourceMap.toString());
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    log.info(field.getName());
                    log.info(field.getDeclaringClass().getCanonicalName());
                    log.info(field.getType().getCanonicalName());
                }
            }
            log.info("耗时：" + tookInMillis);
        } catch (IOException ioe) {
            log.info(ioe.getMessage(), ioe);
        }
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
//        saveBook();
//        readBook();
        readInpatient();
        readObject(Inpatient.class);
    }
}
