package com.databases.synchronizer.repository.implemantation;

import com.databases.synchronizer.repository.Repository;

import java.util.List;

/**
 * Created by Ghazi Naceur on 01/05/2018.
 */
public class CassandraRepository implements Repository{
    @Override
    public Object create(Object entity) {
        return null;
    }

    @Override
    public Object update(Object entity, String table) {
        return null;
    }

    @Override
    public Object getById(String idName, String idValue, String table, Class clazz) {
        return null;
    }

    @Override
    public List getAll(String table, Class clazz) {
        return null;
    }

    @Override
    public void delete(Object entity) {

    }
}
