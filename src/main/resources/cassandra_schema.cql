
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

CREATE TABLE city(
    name text,
    prefecture text,
    country text,
    population bigint,
    PRIMARY KEY (name, prefecture)
);

INSERT INTO person (id, lastname, firstname, age, occupation) VALUES ('7', 'Ging', 'Freecss', 45, 'Hunter');
INSERT INTO person (id, lastname, firstname, age, occupation) VALUES ('3', 'Sishui', 'Uchiha', 27, 'Shinobi');

INSERT INTO address (id, name, street, district, city, postcode) VALUES ('3', 'Assassins street', '3Bis', 'West District', 'Zoldick Ct', 'PC-5421');

INSERT INTO city (name, prefecture, country, population) VALUES ('Yokohama', 'Kanagawa', 'japan', 3689603);
INSERT INTO city (name, prefecture, country, population) VALUES ('Sendai','Miyagi','japan', 1045903);
INSERT INTO city (name, prefecture, country, population) VALUES ('Osaka','Osaka','japan',2666371);
INSERT INTO city (name, prefecture, country, population) VALUES ('Kitakyushu','Fukuoka','japan',977288);
INSERT INTO city (name, prefecture, country, population) VALUES ('Nagoya','Aichi','japan',2263907);
INSERT INTO city (name, prefecture, country, population) VALUES ('Chiba','Chiba','japan',962130);
INSERT INTO city (name, prefecture, country, population) VALUES ('Kobe','Hyōgo','japan',1544873);
INSERT INTO city (name, prefecture, country, population) VALUES ('Niigata','Niigata','japan',812192);
INSERT INTO city (name, prefecture, country, population) VALUES ('Kyoto','Kyoto','japan',1474473);
INSERT INTO city (name, prefecture, country, population) VALUES ('Hamamatsa','Shizuoka','japan',800912);
INSERT INTO city (name, prefecture, country, population) VALUES ('Kumamoto','Kumamoto','japan',734294);
INSERT INTO city (name, prefecture, country, population) VALUES ('Kawasaki','Kanagawa','japan',1425678);
INSERT INTO city (name, prefecture, country, population) VALUES ('Sagamihara','Kanagawa','japan',717561);
INSERT INTO city (name, prefecture, country, population) VALUES ('Saitama','Saitama','japan',1222910);
INSERT INTO city (name, prefecture, country, population) VALUES ('Shizuoka','Shizuoka','japan',716328);
INSERT INTO city (name, prefecture, country, population) VALUES ('Hiroshima','Hiroshima','japan',1174209);

DELETE FROM person WHERE id='7';

------------------------

PUT cities
{
  "mappings": {
      "city": {
        "properties": {
          "name": {
            "type": "text"
          },
          "prefecture": {
            "type": "text"
          },
          "country": {
            "type": "text"
          },
		  "population": {
            "type": "long"
          }
        }
      }
	}
}

---------------------

Basic Synchro :
[
        insert in cassandra -> insert in elasticsearch
        insert in elasticsearch -> delete from elasticsearch

        delete from cassandra -> delete from elasticsearch
        delete from elasticsearch -> insert in elasticsearch
]