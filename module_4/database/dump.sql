CREATE DATABASE gift_certificates_system;

USE gift_certificates_system;

CREATE TABLE gift_certificate (
    id bigint unsigned auto_increment not null,
    name varchar(50) not null,
    description varchar(200) not null,
    price decimal(10,2) not null,
    duration smallint unsigned not null,
    create_date datetime(3) not null,
    last_update_date datetime(3) not null,
    UNIQUE (name, description),
    PRIMARY KEY (id)
);

CREATE TABLE tag (
  id bigint unsigned auto_increment not null,
  name varchar(50) not null,
  UNIQUE (name),
  PRIMARY KEY (id)
);

CREATE TABLE junction (
    gift_certificate_id bigint unsigned not null,
    tag_id bigint unsigned not null,
    PRIMARY KEY (gift_certificate_id, tag_id),
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate(id),
    FOREIGN KEY (tag_id) REFERENCES tag(id)
);

DROP TABLE IF EXISTS gift_certificate;
CREATE TABLE gift_certificate
(
    id               bigint unsigned auto_increment not null,
    name             varchar(50)                    not null,
    description      varchar(200)                   not null,
    price            decimal(10, 2)                 not null,
    duration         smallint unsigned              not null,
    create_date      datetime(3)                    not null,
    last_update_date datetime(3)                    not null,
    UNIQUE (name, description),
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS tag;
CREATE TABLE tag
(
    id   bigint unsigned NOT NULL AUTO_INCREMENT,
    name varchar(50)     NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY name (name)
);

DROP TABLE IF EXISTS gift_certificate_to_tag_relation;
CREATE TABLE gift_certificate_to_tag_relation
(
    gift_certificate_id bigint unsigned not null,
    tag_id              bigint unsigned not null,
    PRIMARY KEY (gift_certificate_id, tag_id),
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id),
    FOREIGN KEY (tag_id) REFERENCES tag (id) 
);