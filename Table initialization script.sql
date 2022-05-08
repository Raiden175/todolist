psql -U postgres -c "CREATE DATABASE TODO WITH OWNER = postgres"

CREATE SCHEMA todo
  AUTHORIZATION postgres;


DROP SEQUENCE IF EXISTS todo_list_seq;

CREATE SEQUENCE todo_list_seq
       INCREMENT BY 1
       MINVALUE 1
       CACHE 1
       NO CYCLE;




DROP TABLE IF EXISTS todo_list CASCADE;

CREATE TABLE todo_list
(
   todo_seqno    bigint     DEFAULT nextval('todo_list_seq'::regclass) NOT NULL,
   todo_code     varchar,
   todo_desc     varchar,
   created_date  timestamp default  now(),
   updated_date  timestamp default  now(),
   created_by    varchar,
   status        varchar   DEFAULT 'OPEN',
   active_flag   char(1)   DEFAULT 'A',
   comp_date     timestamp
);

ALTER TABLE todo_list
   ADD CONSTRAINT todo_pk
   PRIMARY KEY (todo_seqno);

ALTER TABLE todo_list
   ADD CONSTRAINT todo_code_unique
   UNIQUE  (todo_code);   

