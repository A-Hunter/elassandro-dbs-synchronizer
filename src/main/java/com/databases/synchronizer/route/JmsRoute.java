package com.databases.synchronizer.route;

import com.databases.synchronizer.injection.Injector;
import com.databases.synchronizer.repository.implemantation.ElassandroRepository;
import com.databases.synchronizer.repository.implemantation.ElasticsearchRepository;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Ghazi Naceur on 04/05/2018.
 */
@Component
public class JmsRoute  extends RouteBuilder {

    @Autowired
    ElasticsearchRepository repository;

    static final Logger LOGGER = LoggerFactory.getLogger(JmsRoute.class);

    @Override
    public void configure() throws Exception {
        from("activemq:queue:inbound.endpoint")
//                .transacted()
                .log(LoggingLevel.INFO, log, "Received message")
                .process(exchange -> {
                    LOGGER.info("Exchange : {}", exchange);
                })
                .process(exchange -> {
                    Object obj = exchange.getIn().getBody(Object.class);
//                    repository.insertIntoElasticsearchWithFixedId(obj);
                    repository.insertIntoElasticsearch(obj);
                })
                .to("activemq:queue:outbound.endpoint")
                .log(LoggingLevel.INFO, log, "Message sent.")
                .end();
    }
}

