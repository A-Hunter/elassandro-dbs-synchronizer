package com.databases.synchronizer.synchronization;

import com.databases.synchronizer.repository.implemantation.CassandraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Synchronizer<T> {

    @Autowired
    CassandraRepository repository;

    public Boolean synchronize(String table, Class<T> clazz) {

        long scrollTime = 60000;

        if (repository.findAll(clazz, scrollTime) == repository.getAll(table, clazz).size()) {
            System.out.println("You have the same number of records { es : " + repository.findAll(clazz, scrollTime) +
                    ", cass : " + repository.getAll(table, clazz).size() + " }");
            return true;
        }
//        else if (repository.findAll(clazz, scrollTime) == repository.getAll(table, clazz).size()){
//
//        }

        return false;
    }
}
