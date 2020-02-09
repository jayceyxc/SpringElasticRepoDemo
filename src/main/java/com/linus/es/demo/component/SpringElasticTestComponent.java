package com.linus.es.demo.component;

import com.linus.es.demo.documents.Book;
import com.linus.es.demo.documents.Inpatient;
import com.linus.es.demo.documents.Person;
import com.linus.es.demo.repositories.BookRepository;
import com.linus.es.demo.repositories.InpatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;

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
    }
}
