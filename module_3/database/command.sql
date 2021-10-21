CREATE TABLE user (
  id bigint unsigned auto_increment not null,
  name varchar(50) not null,
  UNIQUE (name),
  PRIMARY KEY (id)
);