
use synchronizer;

CREATE TABLE person(
    id text PRIMARY KEY,
    firstname text,
    lastname text,
    age int,
    occupation text
);

CREATE TABLE address(
    id text,
    name text,
    street text,
    district text,
    city text,
    postcode text,
    PRIMARY KEY (id, name, street)
);


INSERT INTO person (id, lastname, firstname, age, occupation) VALUES ('7', 'Ging', 'Freecss', 45, 'Hunter');
INSERT INTO person (id, lastname, firstname, age, occupation) VALUES ('3', 'Sishui', 'Uchiha', 27, 'Shinobi');

INSERT INTO address (id, name, street, district, city, postcode) VALUES ('3', 'Assassins street', '3Bis', 'West District', 'Zoldick Ct', 'PC-5421');

DELETE FROM person WHERE id='7';

Basic Synchro :
[
        insert in cassandra -> insert in elasticsearch
        insert in elasticsearch -> delete from elasticsearch

        delete from cassandra -> delete from elasticsearch
        delete from elasticsearch -> insert in elasticsearch
]