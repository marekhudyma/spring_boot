DROP TABLE IF EXISTS SAMPLE_ENTITY;

CREATE TABLE SAMPLE_ENTITY
(
  id serial PRIMARY KEY,
  created timestamp with time zone,
  last_modified timestamp with time zone,

  name text NOT NULL,
  uuid uuid
)
WITH (
  OIDS=FALSE
);
ALTER TABLE SAMPLE_ENTITY OWNER TO postgres;


