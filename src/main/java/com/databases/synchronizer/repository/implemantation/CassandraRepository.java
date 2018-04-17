package com.databases.synchronizer.repository.implemantation;

import com.databases.synchronizer.repository.Repository;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;

import java.util.List;

@org.springframework.stereotype.Repository
public class CassandraRepository<T> implements Repository<T> {

    @Autowired
    CassandraOperations cassandraOperations;

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public T create(T entity) {
        cassandraOperations.insert(entity);
        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setObject(entity);
        elasticsearchOperations.index(indexQuery);
        return entity;

    }

    @Override
    public T update(T entity) {
        cassandraOperations.update(entity);
        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setObject(entity);
        elasticsearchOperations.index(indexQuery);
        // TODO : I need to check if the document exists in Elasticsearch
//        SearchQuery query = new NativeSearchQueryBuilder()
//                                .withQuery(QueryBuilders.matchAllQuery())
//                                .withFilter()
//                                .build();
//        elasticsearchOperations.queryForList(query, entity.getClass());

        /*
        UpdateQuery updateQuery = new UpdateQuery();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.convertValue(entity, Map.class);
        UpdateRequest request = new UpdateRequest();
        request.doc(map);
        updateQuery.setUpdateRequest(request);
//        updateQuery.setId(entity.getId());
        updateQuery.setClazz(entity.getClass());
        elasticsearchOperations.update(updateQuery);
        */
        return entity;
    }

    @Override
    public T getById(String idName, String idValue, String table, Class<T> clazz) {
        Select select = QueryBuilder.select().from(table);
        select.where(QueryBuilder.eq(idName, idValue));
        return cassandraOperations.selectOne(select, clazz);
    }

    @Override
    public List getAll(String table, Class<T> clazz) {
        Select select = QueryBuilder.select().from(table);
        return cassandraOperations.select(select, clazz);
    }

    @Override
    public void delete(T entity) {

    }


}
