CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE SCHEMA IF NOT EXISTS peterMacdb;

DROP TABLE IF EXISTS peterMacdb.Users;

CREATE TABLE peterMacdb.Users (
    userID uuid CONSTRAINT userId_pk PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(150) NOT NULL,
    password VARCHAR(50) NOT NULL,
    create_date timestamp default now(),
    update_date timestamp default now(),
    AHPRA_num VARCHAR(50),
    provider_num VARCHAR(50),
    prescriber_num VARCHAR(50),
    CONSTRAINT email_unique UNIQUE(email)
);