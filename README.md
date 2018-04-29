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
 &npsp;   `name text,`<br />
 &npsp;   `prefecture text,`<br />
 &npsp;   `country text,`<br />
 &npsp;   `population bigint,`<br />
 &npsp;   `PRIMARY KEY (name, prefecture)`<br />
`);`

### Elasticsearch

At this point, you can create the respective Elasticsearch index for your Cassandra Table, with this command  : <br/>
#### Using kibana :

`PUT cities`<br />
`{`<br />
&lt;p&gt;  `"mappings": {`<br />
&npsp;&npsp;      `"city": {`<br />
&npsp;&npsp;&npsp;        `"properties": {`<br />
&npsp;&npsp;&npsp;          `"name": {`<br />
&npsp;&npsp;&npsp;&npsp;            `"type": "text"`<br />
&npsp;&npsp;&npsp;&npsp;          `},`<br />
&npsp;&npsp;&npsp;          `"prefecture": {`<br />
&npsp;&npsp;&npsp;&npsp;            `"type": "text"`<br />
&npsp;&npsp;&npsp;&npsp;          `},`<br />
&npsp;&npsp;&npsp;          `"country": {`<br />
&npsp;&npsp;&npsp;&npsp;            `"type": "text"`<br />
&npsp;&npsp;&npsp;&npsp;          `},`<br />
&npsp;&npsp;&npsp;		  `"population": {`<br />
&npsp;&npsp;&npsp;&npsp;            `"type": "long"`<br />
&npsp;&npsp;&npsp;          `}`<br />
&npsp;&npsp;        `}`<br />
&npsp;&npsp;      `}`<br />
&npsp;&npsp;	`}`<br />
`}`<br />

#### Using CURL :

`curl -XPUT "http://localhost:9200/cities" -H 'Content-Type: application/json' -d'`<br />
`{`<br />
  `"mappings": {`<br />
      `"city": {`<br />
        `"properties": {`<br />
          `"name": {`<br />
            `"type": "text"`<br />
          `},`<br />
          `"prefecture": {`<br />
            `"type": "text"`<br />
          `},`<br />
          `"country": {`<br />
            `"type": "text"`<br />
          `},`<br />
		  `"population": {`<br />
            `"type": "long"`<br />
          `}`<br />
        `}`<br />
      `}`<br />
	`}`<br />
`}'`<br />
