# Postings schema

# --- !Ups

CREATE SEQUENCE posting_id_seq;
CREATE TABLE posting (
    id integer NOT NULL DEFAULT nextval('posting_id_seq'),
    subject varchar(255),
    description varchar(255),
    userName varchar(255),
    eMail varchar(255),
    phone varchar(255)

);

# --- !Downs

DROP TABLE posting;
DROP SEQUENCE posting_id_seq;