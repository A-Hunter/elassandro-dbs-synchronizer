package com.databases.synchronizer.repository.implemantation;

import com.databases.synchronizer.entity.Person;
import com.databases.synchronizer.jms.Messenger;
import com.databases.synchronizer.repository.Repository;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;

import java.util.List;
import java.util.Map;

/**
 * Created by Ghazi Naceur on 01/05/2018.
 */
@org.springframework.stereotype.Repository
public class CassandraRepository implements Repository{

    static Logger LOGGER = Logger.getLogger(CassandraRepository.class.getName());

    @Autowired
    CassandraOperations cassandraOperations;

    @Autowired
    Messenger messenger;

    @Override
    public Object create(Object entity) {
        try {
            cassandraOperations.insert(entity);
            messenger.send("INPUTQUEUE.D", entity);
            return entity;
        } catch (Exception e) {
            LOGGER.error("Error when trying to create the row '" + entity + "' in Cassandra : " + e + " - " + e.getCause());
        }
        return null;
    }

    @Override
    public Object update(Object entity, String table) {
        try {
        cassandraOperations.update(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public Object getById(String idName, String idValue, String table, Class clazz) {
        try {
            Select select = QueryBuilder.select().from(table);
            select.where(QueryBuilder.eq(idName, idValue));
            return cassandraOperations.selectOne(select, clazz);
        } catch (Exception e) {
            LOGGER.error("Error when trying to retrieve the row with the id '" + idName + ":" + idValue + "' from Cassandra : " + e + " - " + e.getCause());
        }
        return null;
    }

    @Override
    public List getAll(String table, Class clazz) {
        try {
            Select select = QueryBuilder.select().from(table);
            return cassandraOperations.select(select, clazz);
        } catch (Exception e) {
            LOGGER.error("Error when trying to retrieve all rows of the table '" + table + "' from Cassandra : " + e + " - " + e.getCause());
        }
        return null;
    }

    @Override
    public void delete(Object entity) {
        cassandraOperations.delete(entity);
    }
}
