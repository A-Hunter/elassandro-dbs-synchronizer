package com.databases.synchronizer.repository;

import java.util.List;

public interface Repository<T> {

    T create(T entity);

    T update(T entity, String table);

    T getById(String idName, String idValue, String table, Class<T> clazz);

    List<T> getAll(String table, Class<T> clazz);

    void delete(T entity);
}
