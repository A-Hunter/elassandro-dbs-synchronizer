package com.databases.synchronizer.injection;

import com.databases.synchronizer.repository.implemantation.ElasticsearchRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Ghazi Naceur on 04/05/2018.
 */
public class Injector implements Processor{

    @Autowired
    ElasticsearchRepository repository;

    @Override
    public void process(Exchange exchange) throws Exception {
        Object obj = exchange.getIn().getBody(Object.class);
        repository.create(obj);
//        obj += " ... processing finished !";
//        exchange.getIn().setBody(obj);
    }
}
