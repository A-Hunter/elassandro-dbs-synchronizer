package com.databases.synchronizer.utils;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Collectors;

import com.databases.synchronizer.annotation.OrdinaryField;
import com.databases.synchronizer.annotation.PrimaryKey;
import com.databases.synchronizer.entity.Model;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import org.apache.log4j.Logger;

/**
 * Created by Ghazi Naceur on 11/08/2018.
 */

public final class QueryHelper {

    static Logger LOGGER = Logger.getLogger(QueryHelper.class.getName());

    private QueryHelper() {
        super();
    }

    public static void insertQuery(Model model, String table, Session session){

        StringBuilder query = new StringBuilder("INSERT into "+table.toUpperCase()+" (");
        Map<String, Object> context = model.toMap();

        String keysClause = model.toMap().entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null)
                .map(entry -> entry.getKey())
                .collect(Collectors.joining(","));

        query.append(keysClause).append(") VALUES ");

        List<String> keys = Arrays.asList(keysClause.split(","));

        StringJoiner joiner = new StringJoiner(",", "(", ")");
        keys.forEach(key -> joiner.add("?"));
        query.append(joiner).append(";");

        PreparedStatement statement = session.prepare(query.toString());
        BoundStatement boundStatement = new BoundStatement(statement);

        System.out.println(query.toString());

        int j = 0;
        for(int i = 0 ; i < keys.size() ; i++){
            if (context.get(keys.get(i)) instanceof String){
                boundStatement.setString(j++, (String) context.get(keys.get(i)));
            } else if (context.get(keys.get(i)) instanceof Map){
                boundStatement.setMap(j++, (Map) context.get(keys.get(i)));
            } else if (context.get(keys.get(i)) instanceof Date){
                boundStatement.setTimestamp(j++, (Date) context.get(keys.get(i)));
            } else if (context.get(keys.get(i)) instanceof Integer){
                boundStatement.setInt(j++, (int) context.get(keys.get(i)));
            } else if (context.get(keys.get(i)) instanceof Double){
                boundStatement.setDouble(j++, (double) context.get(keys.get(i)));
            } else if (context.get(keys.get(i)) instanceof List){
                boundStatement.setList(j++, (List) context.get(keys.get(i)));
            } else if (context.get(keys.get(i)) instanceof Long){
                boundStatement.setLong(j++,  (long) context.get(keys.get(i)));
            } else if (context.get(keys.get(i)) instanceof Float){
                boundStatement.setFloat(j++, (float) context.get(keys.get(i)));
            } else if (context.get(keys.get(i)) instanceof ByteBuffer){
                boundStatement.setBytes(j++, (ByteBuffer) context.get(keys.get(i)));
            } else if (context.get(keys.get(i)) instanceof Set){
                boundStatement.setSet(j++, (Set) context.get(keys.get(i)));
            } else if (context.get(keys.get(i)) instanceof Short){
                boundStatement.setShort(j++, (short) context.get(keys.get(i)));
            } else if (context.get(keys.get(i)) instanceof UUID){
                boundStatement.setUUID(j++, (UUID) context.get(keys.get(i)));
            }
        }
        session.execute(boundStatement);
    }


    public static void updateQuery(Model model, String table, Session session){

        StringBuilder query = new StringBuilder("UPDATE "+table.toUpperCase()+" SET ");
        Map<String, Object> context = model.toMap();

        String whereClause = "";
        String setClause = "";
        List<String> primaryKeys = new ArrayList<>();
        List<String> primaryKeyAndValues = new ArrayList<>();
        List<String> allKeys = new ArrayList<>();
        List<String> ordinaryKeys = new ArrayList<>();
        List<String> ordinaryKeysAndValues = new ArrayList<>();

        try {
            Field[] fields = model.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(PrimaryKey.class)) {
                    PrimaryKey pk = field.getAnnotation(PrimaryKey.class);
                    String fieldName = pk.fieldName();
                    primaryKeys.add(field.getName());
                    primaryKeyAndValues.add(fieldName + "='" + context.get(fieldName)+"'");
                } else if (field.isAnnotationPresent(OrdinaryField.class)) {

                    OrdinaryField of = field.getAnnotation(OrdinaryField.class);
                    String ordinaryFieldName = of.fieldName();
                    if(field.get(model) != null){
                        ordinaryKeys.add(ordinaryFieldName);
                        ordinaryKeysAndValues.add(ordinaryFieldName + "=?");
                    }
                }
            }

            whereClause = primaryKeyAndValues.stream().collect(Collectors.joining(" AND "));
            setClause = ordinaryKeysAndValues.stream().collect(Collectors.joining(", "));

            model.toMap().entrySet()
                    .stream()
                    .filter(entry -> entry.getValue() != null)
                    .forEach(entry -> allKeys.add(entry.getKey()));

            System.out.println("Fields with PKs : " + allKeys.stream().map(entry -> entry).collect(Collectors.joining(",")));

            String request = query.toString() +" "+ setClause +" WHERE "+  whereClause +";";
            System.out.println(request);

            PreparedStatement statement = session.prepare(request);
            BoundStatement boundStatement = new BoundStatement(statement);

            int j = 0;
            for(int i = 0 ; i < ordinaryKeys.size() ; i++){
                if (context.get(ordinaryKeys.get(i)) instanceof String){
                    boundStatement.setString(j++, (String) context.get(ordinaryKeys.get(i)));
                } else if (context.get(ordinaryKeys.get(i)) instanceof Map){
                    boundStatement.setMap(j++, (Map) context.get(ordinaryKeys.get(i)));
                } else if (context.get(ordinaryKeys.get(i)) instanceof Date){
                    boundStatement.setTimestamp(j++, (Date) context.get(ordinaryKeys.get(i)));
                } else if (context.get(ordinaryKeys.get(i)) instanceof Integer){
                    boundStatement.setInt(j++, (int) context.get(ordinaryKeys.get(i)));
                } else if (context.get(ordinaryKeys.get(i)) instanceof Double){
                    boundStatement.setDouble(j++, (double) context.get(ordinaryKeys.get(i)));
                } else if (context.get(ordinaryKeys.get(i)) instanceof List){
                    boundStatement.setList(j++, (List) context.get(ordinaryKeys.get(i)));
                } else if (context.get(ordinaryKeys.get(i)) instanceof Long){
                    boundStatement.setLong(j++,  (long) context.get(ordinaryKeys.get(i)));
                } else if (context.get(ordinaryKeys.get(i)) instanceof Float){
                    boundStatement.setFloat(j++, (float) context.get(ordinaryKeys.get(i)));
                } else if (context.get(ordinaryKeys.get(i)) instanceof ByteBuffer){
                    boundStatement.setBytes(j++, (ByteBuffer) context.get(ordinaryKeys.get(i)));
                } else if (context.get(ordinaryKeys.get(i)) instanceof Set){
                    boundStatement.setSet(j++, (Set) context.get(ordinaryKeys.get(i)));
                } else if (context.get(ordinaryKeys.get(i)) instanceof Short){
                    boundStatement.setShort(j++, (short) context.get(ordinaryKeys.get(i)));
                } else if (context.get(ordinaryKeys.get(i)) instanceof UUID){
                    boundStatement.setUUID(j++, (UUID) context.get(ordinaryKeys.get(i)));
                }
            }
            session.execute(boundStatement);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }
}

