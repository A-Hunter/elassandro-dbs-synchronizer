CREATE TABLE person(
    id text PRIMARY KEY,
    firstname text,
    lastname text,
    age int,
    occupation text
);

INSERT INTO person (id, lastname, firstname, age, occupation) VALUES ('7', 'Ging', 'Freecss', 45, 'Hunter');
INSERT INTO person (id, lastname, firstname, age, occupation) VALUES ('4', 'Sishui', 'Uchiha', 27, 'Shinobi');

DELETE FROM person WHERE id='7';

Basic Synchro :
[
        insert in cassandra -> insert in elasticsearch
        insert in elasticsearch -> delete from elasticsearch

        delete from cassandra -> delete from elasticsearch
        delete from elasticsearch -> insert in elasticsearch
]