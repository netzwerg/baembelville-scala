# Postings schema

# --- !Ups

CREATE TABLE posting (
    id varchar(255) NOT NULL,
    verified boolean,
    subject varchar(255),
    description varchar(255),
    userName varchar(255),
    eMail varchar(255),
    phone varchar(255)

);

# --- !Downs

DROP TABLE posting;