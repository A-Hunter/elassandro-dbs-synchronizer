package com.databases.synchronizer.repository.implemantation;

import com.databases.synchronizer.entity.Entity;
import com.databases.synchronizer.repository.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;

import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Repository
public class CassandraRepository implements Repository {

    @Autowired
    CassandraOperations cassandraOperations;

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Override
    public Entity create(Entity entity) {
        cassandraOperations.insert(entity);
        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setObject(entity);
        elasticsearchOperations.index(indexQuery);
        return entity;
    }

    @Override
    public Entity update(Entity entity) {
        cassandraOperations.update(entity);
        UpdateQuery updateQuery = new UpdateQuery();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.convertValue(entity, Map.class);
        UpdateRequest request = new UpdateRequest();
        request.doc(map);
        updateQuery.setUpdateRequest(request);
        updateQuery.setId(entity.getId());
        updateQuery.setClazz(entity.getClass());
        elasticsearchOperations.update(updateQuery);
        return entity;
    }

    @Override
    public Entity getById(String id) {
        return null;
    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public void delete(Entity entity) {

    }
}
