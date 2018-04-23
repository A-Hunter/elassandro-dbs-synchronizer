package com.databases.synchronizer.synchronization;

import com.databases.synchronizer.repository.implemantation.CassandraRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Synchronizer<T> {

    static Logger LOGGER = Logger.getLogger(Synchronizer.class.getName());

    @Autowired
    CassandraRepository repository;

    public Boolean synchronize(String table, Class<T> clazz) {

        long scrollTime = 60000;
        try {
            if (repository.findAll(clazz, scrollTime).size() == repository.getAll(table, clazz).size()) {
                System.out.println("You have the same number of records { es : " + repository.findAll(clazz, scrollTime).size() +
                        ", cass : " + repository.getAll(table, clazz).size() + " }");
                return true;
            } else if (repository.findAll(clazz, scrollTime).size() > repository.getAll(table, clazz).size()) {
                List<T> fromElasticsearch = repository.findAll(clazz, scrollTime);
                List<T> fromCassandra = repository.getAll(table, clazz);
                Set<Object> inconsistentResults = new HashSet<>();

                // TODO : This is just a note : We will consider Cassandra as a first
                // TODO                data storage, so the number of documents in Elasticsearch
                // TODO                will adjusted compared to the number of rows in Cassandra

                List<Object> idsFromElasticsearch = new ArrayList<>();
                List<Object> idsFromCassandra = new ArrayList<>();

                fromElasticsearch.forEach(t -> {
                    try {
                        Field field1 = t.getClass().getDeclaredField("id");
                        field1.setAccessible(true);
                        Object value1 = field1.get(t);
                        idsFromElasticsearch.add(value1);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                });
                fromCassandra.forEach(p -> {
                    try {
                        Field field2 = p.getClass().getDeclaredField("id");
                        field2.setAccessible(true);
                        Object value2 = field2.get(p);
                        idsFromCassandra.add(value2);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                });

                idsFromElasticsearch.forEach(id -> {
                    if (!idsFromCassandra.contains(id)) {
                        inconsistentResults.add(id);
                        repository.deleteFromElasticsearch(clazz, id);
                    }
                });
            } else if (repository.getAll(table, clazz).size() > repository.findAll(clazz, scrollTime).size()) {
                List<T> fromElasticsearch = repository.findAll(clazz, scrollTime);
                List<T> fromCassandra = repository.getAll(table, clazz);

                fromCassandra.forEach(entity -> {
                    if (!fromElasticsearch.contains(entity)) {
                        repository.insertInElasticsearch(entity);
                    }
                });
            }
        } catch (Exception e) {
            LOGGER.error("Error when trying to synchronize the table '" + table + "' : " + e + " - " + e.getCause());
        }
        return false;
    }
}
