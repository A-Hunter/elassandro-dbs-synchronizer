package com.databases.synchronizer.repository.implemantation;

import com.databases.synchronizer.entity.Entity;
import com.databases.synchronizer.repository.Repository;

import java.util.List;

public class ElasticsearchRepository<T extends Entity> implements Repository<T> {

    @Override
    public T create(T entity) {
        return null;
    }

    @Override
    public T update(T entity) {
        return null;
    }

    @Override
    public T getById(String idName, String idValue, String table, Class<T> clazz) {
        return null;
    }

    @Override
    public List<T> getAll() {
        return null;
    }

    @Override
    public void delete(Entity entity) {

    }
}
