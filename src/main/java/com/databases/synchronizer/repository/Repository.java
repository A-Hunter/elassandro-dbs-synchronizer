package com.databases.synchronizer.repository;

import java.util.List;

public interface Repository<T> {

    T create(T entity);

    T update(T entity, String idName, String idValue, String table, Class<T> clazz);

    T getById(String idName, String idValue, String table, Class<T> clazz);

    List<T> getAll(String table, Class<T> clazz);

    void delete(String index, String type, String id, Class<T> clazz);
}
