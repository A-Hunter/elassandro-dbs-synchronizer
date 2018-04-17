package com.databases.synchronizer.repository;

import java.util.List;

public interface Repository<T> {

    T create(T entity);

    T update(T entity);

    T getById(String idName, String idValue, String table, Class<T> clazz);

    List<T> getAll();

    void delete(T entity);
}
