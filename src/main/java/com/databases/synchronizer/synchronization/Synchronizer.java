package com.databases.synchronizer.synchronization;

import com.databases.synchronizer.repository.implemantation.CassandraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Synchronizer<T> {

    @Autowired
    CassandraRepository repository;

    public Boolean synchronize(String table, Class<T> clazz) {

        long scrollTime = 60000;

        if (repository.findAll(clazz, scrollTime).size() == repository.getAll(table, clazz).size()) {
            System.out.println("You have the same number of records { es : " + repository.findAll(clazz, scrollTime).size() +
                    ", cass : " + repository.getAll(table, clazz).size() + " }");
            return true;
        } else if (repository.findAll(clazz, scrollTime).size() > repository.getAll(table, clazz).size()){
            List<T> fromElasticsearch = repository.findAll(clazz, scrollTime);
            List<T> fromCassandra = repository.getAll(table, clazz);

            fromElasticsearch.forEach(entity -> {
                if (!fromCassandra.contains(entity)){
                    repository.insertInCassandra(entity);
                }
            });
        } else if (repository.getAll(table, clazz).size() > repository.findAll(clazz, scrollTime).size()){
            List<T> fromElasticsearch = repository.findAll(clazz, scrollTime);
            List<T> fromCassandra = repository.getAll(table, clazz);

            fromCassandra.forEach(entity -> {
                if (!fromElasticsearch.contains(entity)){
                    repository.insertInElasticsearch(entity);
                }
            });
        }


            return false;
    }
}
