package com.databases.synchronizer.repository;

import com.databases.synchronizer.entity.Entity;
import com.databases.synchronizer.entity.Person;

import java.util.List;

public interface Repository<T extends Entity> {

    T create(T entity);
    T update(T entity);
    T getById(String id);
    List<T> getAll();
    void delete(Entity entity);
}
