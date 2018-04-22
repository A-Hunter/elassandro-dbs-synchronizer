package com.databases.synchronizer.repository.implemantation;

import com.databases.synchronizer.repository.Repository;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ScrolledPage;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Repository
public class CassandraRepository<T> implements Repository<T> {

    @Autowired
    CassandraOperations cassandraOperations;

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

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
        insertInElasticsearch(entity);
        return entity;
    }

    @Override
    public T getById(String idName, String idValue, String table, Class<T> clazz) {
        Select select = QueryBuilder.select().from(table);
        select.where(QueryBuilder.eq(idName, idValue));
        return cassandraOperations.selectOne(select, clazz);
    }

    @Override
    public List<T> getAll(String table, Class<T> clazz) {
        Select select = QueryBuilder.select().from(table);
        return cassandraOperations.select(select, clazz);
    }

    @Override
    public void delete(String index, String type, String id, Class<T> clazz) {
        cassandraOperations.deleteById(id, clazz);
        elasticsearchOperations.delete(clazz, id);
    }

    public List<T> findAll(Class<T> clazz, long scrollTime) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .build();

        List<T> result = new ArrayList<>();
        Page<T> entities = elasticsearchOperations.startScroll(scrollTime, searchQuery, clazz);
        if (entities != null && entities.getContent().size() > 0) {
            String scrollId = ((ScrolledPage<T>) entities).getScrollId();
            boolean stillHasDocuments = true;
            while (stillHasDocuments) {
                result.addAll(entities.getContent());
                entities = scroll(scrollId, scrollTime, clazz);
                if (entities == null || entities.getContent().size() <= 0) {
                    stillHasDocuments = false;
                }
            }
        }
        return result;
    }

    private Page<T> scroll(String scrollId, long scrollTime, Class<T> clazz) {
        try {
            return elasticsearchOperations.continueScroll(scrollId, scrollTime, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteFromElasticsearch(Class<T> clazz, Object entity) {
        elasticsearchOperations.delete(clazz, (String) entity);
    }

    public void insertInCassandra(T entity) {
        cassandraOperations.insert(entity);
    }

    public void insertInElasticsearch(T entity) {
        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setObject(entity);
        elasticsearchOperations.index(indexQuery);
    }
}
