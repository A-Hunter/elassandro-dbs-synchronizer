# Elassandro Databases Synchronizer

Elassandro is a basic databases synchronizer, that guarantees data synchronization between Cassandra and Elasticsearch.

## Structure

Cassandra is considered as a primary data storage. Every inserted row in Cassandra will has its own respective document stored in Elasticsearch.
Following the same logic, each update or delete method invoked on Cassandra, will systematically be invoked on Elasticsearch.

