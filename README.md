# Elassandro Databases Synchronizer - Advanced synchronizer

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
    &nbsp;&nbsp; `name text,`<br />
    &nbsp;&nbsp; `prefecture text,`<br />
    &nbsp;&nbsp; `country text,`<br />
    &nbsp;&nbsp; `population bigint,`<br />
    &nbsp;&nbsp; `PRIMARY KEY (name, prefecture)`<br />
`);`

### Elasticsearch

At this point, you can create the respective Elasticsearch index for your Cassandra Table, with this command  : <br/>
#### Using kibana :

`PUT cities`<br />
`{`<br />
  &nbsp;&nbsp;`"mappings": {`<br />
      &nbsp;&nbsp;`"city": {`<br />
        &nbsp;&nbsp;&nbsp;&nbsp;`"properties": {`<br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`"name": {`<br />
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`"type": "text"`<br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`},`<br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`"prefecture": {`<br />
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`"type": "text"`<br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`},`<br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`"country": {`<br />
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`"type": "text"`<br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`},`<br />
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`"population": {`<br />
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`"type": "long"`<br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`}`<br />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`}`<br />
      &nbsp;&nbsp;&nbsp;&nbsp;`}`<br />
	&nbsp;&nbsp;`}`<br />
&nbsp;&nbsp;`}`<br />

#### Using CURL :

`curl -XPUT "http://localhost:9200/cities" -H 'Content-Type: application/json' -d'`<br />
`{`<br />
  &nbsp;&nbsp;`"mappings": {`<br />
      &nbsp;&nbsp;&nbsp;&nbsp;`"city": {`<br />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`"properties": {`<br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`"name": {`<br />
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`"type": "text"`<br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`},`<br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`"prefecture": {`<br />
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`"type": "text"`<br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`},`<br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`"country": {`<br />
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`"type": "text"`<br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`},`<br />
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`"population": {`<br />
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`"type": "long"`<br />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`}`<br />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`}`<br />
      &nbsp;&nbsp;&nbsp;&nbsp;`}`<br />
	&nbsp;&nbsp;`}`<br />
`}'`<br />