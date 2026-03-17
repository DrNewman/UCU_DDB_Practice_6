CREATE KEYSPACE IF NOT EXISTS shop_space
WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

USE shop_space;

CREATE TABLE IF NOT EXISTS hits_counter (
    counter_id text PRIMARY KEY,
    value counter
);