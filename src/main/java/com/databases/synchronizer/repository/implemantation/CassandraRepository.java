package com.databases.synchronizer.repository.implemantation;

import com.databases.synchronizer.repository.Repository;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import org.apache.log4j.Logger;
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

    static Logger LOGGER = Logger.getLogger(CassandraRepository.class.getName());

    @Autowired
    CassandraOperations cassandraOperations;

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Override
    public T create(T entity) {
        try {
            cassandraOperations.insert(entity);
            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setObject(entity);
            elasticsearchOperations.index(indexQuery);
            return entity;
        } catch (Exception e) {
            LOGGER.error("Error when trying to insert the '" + entity + "' : " + e + " - " + e.getCause());
            return null;
        }
    }

    @Override
    public T update(T entity, String idName, String idValue, String table, Class<T> clazz) {

        T obj = getById(idName, idValue, table, clazz);
        try {
            cassandraOperations.update(entity);
        } catch (Exception e) {
            LOGGER.error("Error when trying to update the row '" + entity + "' in Cassandra : " + e + " - " + e.getCause());
        }
        try {
            insertInElasticsearch(entity);
        } catch (Exception e) {
            // The purpose of deleting the document
            cassandraOperations.update(obj);
            LOGGER.error("Error when trying to update the document '" + entity + "' in Elasticsearch : " + e + " - " + e.getCause());
        }
        return entity;
    }

    @Override
    public T getById(String idName, String idValue, String table, Class<T> clazz) {
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
    public List<T> getAll(String table, Class<T> clazz) {
        try {
            Select select = QueryBuilder.select().from(table);
            return cassandraOperations.select(select, clazz);
        } catch (Exception e) {
            LOGGER.error("Error when trying to retrieve all rows of the table '" + table + "' from Cassandra : " + e + " - " + e.getCause());
        }
        return null;
    }

    @Override
    public void delete(String index, String type, String id, Class<T> clazz) {
        try {
            cassandraOperations.deleteById(id, clazz);
            elasticsearchOperations.delete(clazz, id);
        } catch (Exception e) {
            LOGGER.error("Error when trying to delete the entity with the id '" + id + "' : " + e + " - " + e.getCause());
        }
    }

    public List<T> findAll(Class<T> clazz, long scrollTime) {
        try {
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
        } catch (Exception e) {
            LOGGER.error("Error when trying to retrieve all documents related to the entity '" + clazz.getSimpleName() + "' from Elasticsearch : " + e + " - " + e.getCause());
        }
        return null;
    }

    private Page<T> scroll(String scrollId, long scrollTime, Class<T> clazz) {
        try {
            return elasticsearchOperations.continueScroll(scrollId, scrollTime, clazz);
        } catch (Exception e) {
            LOGGER.error("Error when trying to scroll with the scroll_id :'" + scrollId + "' in order to retrieve documents related to the entity '" + clazz.getSimpleName() + "' from Elasticsearch : " + e + " - " + e.getCause());
        }
        return null;
    }

    public void deleteFromElasticsearch(Class<T> clazz, Object entity) {
        try {
            elasticsearchOperations.delete(clazz, (String) entity);
        } catch (Exception e) {
            LOGGER.error("Error when trying to delete the document :'" + entity + "' from Elasticsearch : " + e + " - " + e.getCause());
        }
    }

    public void insertInCassandra(T entity) {
        try {
            cassandraOperations.insert(entity);
        } catch (Exception e) {
            LOGGER.error("Error when trying to insert the '" + entity + "' : " + e + " - " + e.getCause());
        }
    }

    public void insertInElasticsearch(T entity) {
        try {
            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setObject(entity);
            elasticsearchOperations.index(indexQuery);
        } catch (Exception e) {
            LOGGER.error("Error when trying to insert the '" + entity + "' : " + e + " - " + e.getCause());
        }
    }
}
