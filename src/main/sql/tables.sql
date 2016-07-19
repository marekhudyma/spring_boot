DROP TABLE IF EXISTS USER_ROLE;
DROP TABLE IF EXISTS USER_ACCOUNT;
DROP TABLE IF EXISTS ROLE;
DROP TABLE IF EXISTS SAMPLE_ENTITY;

CREATE TABLE USER_ACCOUNT
(
  id serial PRIMARY KEY,
  external_id text,
  created timestamp with time zone NOT NULL,
  last_modified timestamp with time zone NOT NULL,
  name text,
  login text UNIQUE,
  password text
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
INSERT INTO ROLE(id, created, last_modified, authority) VALUES (1, NOW(), NOW(), 'ROLE_USER'); --IT NEEDS TO BE "ROLE_"
INSERT INTO ROLE(id, created, last_modified, authority) VALUES (2, NOW(), NOW(), 'ROLE_ADMIN');

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
INSERT INTO user_account(id, external_id, created, last_modified, name, login, password) VALUES (1, null, now(), now(), 'marekUser', 'user', 'u');
INSERT INTO user_role(id, user_id, role_id) VALUES (1, 1, 1);

INSERT INTO user_account(id, external_id, created, last_modified, name, login, password) VALUES (2, null, now(), now(), 'marekAdmin', 'admin', 'a');
INSERT INTO user_role(id, user_id, role_id) VALUES (2, 2, 1);
INSERT INTO user_role(id, user_id, role_id) VALUES (3, 2, 2);













