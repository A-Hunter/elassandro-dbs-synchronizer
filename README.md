# Elassandro Databases Synchronizer

Elassandro is a basic databases synchronizer, that guarantees data synchronization between Cassandra and Elasticsearch.

## Structure

Cassandra is considered as a primary data storage. Every inserted row in Cassandra will has its own respective document stored in Elasticsearch.
Following the same logic, each update or delete method invoked on Cassandra, will systematically be invoked on Elasticsearch.

## Configuration

This API is built over Cassandra 3.0.9 and Elasticsearch 5.4.1.

### Cassandra

Before running this API, you can create a keyspace using this command : <br/>
`CREATE  KEYSPACE  your_keyspace_name;`<br/>
`USE  your_keyspace_name;`

After that, you can create your table ; Example :

`CREATE TABLE city(`<br />
    `name text,`<br />
    `prefecture text,`<br />
    `country text,`<br />
    `population bigint,`<br />
    `PRIMARY KEY (name, prefecture)`<br />
`);`