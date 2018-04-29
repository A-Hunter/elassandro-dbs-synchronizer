package com.databases.synchronizer.synchronization;

import com.databases.synchronizer.repository.implemantation.CassandraRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Ghazi Ennacer on 21/04/2018.
 * Email: ghazi.ennacer@gmail.com
 */

@Component
public class Synchronizer<T> {

    static Logger LOGGER = Logger.getLogger(Synchronizer.class.getName());

    @Autowired
    CassandraRepository repository;

    public Boolean synchronize(String index, String table, Class<T> clazz) {

        long scrollTime = 60000;
        try {
            if (repository.findAll(clazz, scrollTime).size() == repository.getAll(table, clazz).size()) {
                System.out.println("You have the same number of records { es : " + repository.findAll(clazz, scrollTime).size() +
                        ", cass : " + repository.getAll(table, clazz).size() + " }");
                return true;
            } else if (repository.findAll(clazz, scrollTime).size() > repository.getAll(table, clazz).size()) {

                List<String> resCass = repository.getAllCassandraIds(table, clazz);
                List<String> resElastic = repository.findAllElasticsearchIds(index);

                resElastic.forEach(id -> {
                    if (!resCass.contains(id)) {
                        repository.deleteFromElasticsearch(clazz, id);
                    }
                });

            } else if (repository.getAll(table, clazz).size() > repository.findAll(clazz, scrollTime).size()) {
                List<T> fromElasticsearch = repository.findAll(clazz, scrollTime);
                List<T> fromCassandra = repository.getAll(table, clazz);

                fromCassandra.forEach(entity -> {
                    if (!fromElasticsearch.contains(entity)) {
                        repository.insertIntoElasticsearch(entity);
                    }
                });
            }
        } catch (Exception e) {
            LOGGER.error("Error when trying to synchronize the table '" + table + "' : " + e + " - " + e.getCause());
        }
        return false;
    }
}
