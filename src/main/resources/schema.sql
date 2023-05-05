CREATE SCHEMA IF NOT EXISTS PUBLIC;
USE PUBLIC;

DROP TABLE IF EXISTS CUSTOMER;

CREATE TABLE IF NOT EXISTS CUSTOMER (
  uuid VARCHAR(200) PRIMARY KEY,
  email VARCHAR(100) NOT NULL,
  name VARCHAR(50) NOT NULL,
  lastName VARCHAR(50) NOT NULL
);
