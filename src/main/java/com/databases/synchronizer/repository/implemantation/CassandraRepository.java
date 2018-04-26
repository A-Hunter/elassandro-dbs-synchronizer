package com.databases.synchronizer.repository.implemantation;

import com.databases.synchronizer.repository.Repository;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ScrolledPage;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Repository
public class CassandraRepository<T> implements Repository<T> {

    static Logger LOGGER = Logger.getLogger(CassandraRepository.class.getName());

    @Autowired
    CassandraOperations cassandraOperations;

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Override
    public T create(T entity) {
            cassandraOperations.insert(entity);
            insertIntoElasticsearch(entity);
            return entity;
    }

    @Override
    public T update(T entity, String table) {

        Map<String, String> cassandraId = getCassandraId(entity);

        T oldEntity = getById(cassandraId, table, (Class<T>) entity.getClass());

        String esId = getElasticId(entity);
        cassandraOperations.update(entity);
        UpdateRequest req = new UpdateRequest();
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> map = oMapper.convertValue(entity, Map.class);
        req.doc(map);
        UpdateQuery request = new UpdateQuery();
        request.setId(esId);
        request.setUpdateRequest(req);
        request.setClazz(entity.getClass());
        try{
            elasticsearchOperations.update(request);
        } catch (Exception e){
            cassandraOperations.update(oldEntity);
            e.printStackTrace();
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

    public T getById(Map<String, String> ids, String table, Class<T> clazz) {
        try {
            Select select = QueryBuilder.select().from(table);
            for (Map.Entry<String, String> entry : ids.entrySet()) {
                select.where(QueryBuilder.eq(entry.getKey(), entry.getValue()));
            }
            return cassandraOperations.selectOne(select, clazz);
        } catch (Exception e) {
            LOGGER.error("Error when trying to retrieve the row from Cassandra : " + e + " - " + e.getCause());
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
    public void delete(T entity) {
        String esId = getElasticId(entity);
        elasticsearchOperations.delete(entity.getClass(), esId);
        cassandraOperations.delete(entity);
    }

    private String getElasticId(T entity) {
        String esId = "";
        try {
            Field[] fields = entity.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(PrimaryKeyColumn.class) || field.isAnnotationPresent(PrimaryKey.class)) {
                    esId += field.get(entity);
                }
            }
            esId = esId.replaceAll("\\s+", "");
        } catch (Exception e) {
            LOGGER.error("Error when trying to retrieve the id '" + esId + "' from the entity '"+entity+"' :: " + e + " - " + e.getCause());
        }
        return esId;
    }

    private Map<String, String> getCassandraId(T entity) {
        Map<String, String> cassId = new HashMap<>();
        try {
            Field[] fields = entity.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(PrimaryKeyColumn.class) || field.isAnnotationPresent(PrimaryKey.class)) {
                    cassId.put(field.getName(),(String) field.get(entity));
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error when trying to retrieve the id '" + cassId + "' from the entity '"+entity+"' :: " + e + " - " + e.getCause());
        }
        return cassId;
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

    public void insertIntoElasticsearch(T entity) {
        try {
            String esId = getElasticId(entity);

            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setObject(entity);
            indexQuery.setId(esId);
            elasticsearchOperations.index(indexQuery);
        } catch (Exception e) {
            LOGGER.error("Error when trying to insert the '" + entity + "' : " + e + " - " + e.getCause());
        }
    }
}
