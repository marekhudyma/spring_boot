
DROP TABLE IF EXISTS USER_ROLE;
DROP TABLE IF EXISTS USER_ACCOUNT;
DROP TABLE IF EXISTS ROLE;
DROP TABLE IF EXISTS SAMPLE_ENTITY;

CREATE TABLE USER_ACCOUNT
(
  id serial PRIMARY KEY,
  created timestamp with time zone,        --TODO HUDYMA - NOT NULL
  last_modified timestamp with time zone,  --TODO HUDYMA - NOT NULL
  name text NOT NULL,
  login text NOT NULL,
  password text NOT NULL
) WITH (OIDS=FALSE);
ALTER TABLE USER_ACCOUNT OWNER TO postgres;

CREATE TABLE ROLE
(
  id serial PRIMARY KEY,
  created timestamp with time zone,
  last_modified timestamp with time zone,
  authority character varying(255) NOT NULL
) WITH (OIDS=FALSE);
ALTER TABLE ROLE OWNER TO postgres;
INSERT INTO ROLE(id, created, last_modified, authority) VALUES (1, NOW(), NOW(), 'USER');
INSERT INTO ROLE(id, created, last_modified, authority) VALUES (2, NOW(), NOW(), 'ADMIN');

CREATE TABLE USER_ROLE
(
  id serial PRIMARY KEY,
  user_id bigint NOT NULL,
  role_id bigint NOT NULL,
  CONSTRAINT fk_USER_ROLE_user_id_USER_ACCOUNT_id FOREIGN KEY (user_id) REFERENCES USER_ACCOUNT (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_USER_ROLE_role_id_id_ROLE_id FOREIGN KEY (role_id) REFERENCES ROLE (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
) WITH (OIDS=FALSE);
ALTER TABLE USER_ROLE OWNER TO postgres;

CREATE TABLE SAMPLE_ENTITY
(
  id serial PRIMARY KEY,
  created timestamp with time zone,
  last_modified timestamp with time zone,
  name text NOT NULL,
  uuid uuid
)
WITH (OIDS=FALSE);
ALTER TABLE SAMPLE_ENTITY OWNER TO postgres;

----
INSERT INTO user_account(name, login, password) VALUES ('marek', 'user', 'password');














