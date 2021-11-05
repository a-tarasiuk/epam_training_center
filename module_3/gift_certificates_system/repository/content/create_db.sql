DROP DATABASE IF EXISTS gift_certificates_system_db;

CREATE DATABASE gift_certificates_system_db;

USE gift_certificates_system_db;

/* Entities */
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

CREATE TABLE tag
(
    id   bigint unsigned auto_increment not null,
    name varchar(50)                    not null,
    UNIQUE (name),
    PRIMARY KEY (id)
);

CREATE TABLE user
(
    id         bigint unsigned auto_increment not null,
    first_name varchar(50)                    not null,
    last_name  varchar(50)                    not null,
    UNIQUE (first_name, last_name),
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`
(
    id                  bigint unsigned auto_increment not null,
    user_id             bigint unsigned                not null,
    gift_certificate_id bigint unsigned                not null,
    price               decimal(10, 2)                 not null,
    purchase_date       datetime(3)                    not null,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id)
);

/** Gift certificate and Tag entities - Many-to-many relations */
DROP TABLE IF EXISTS `gift_certificate_to_tag_relation`;
CREATE TABLE gift_certificate_to_tag_relation
(
    gift_certificate_id bigint unsigned not null,
    tag_id              bigint unsigned not null,
    PRIMARY KEY (gift_certificate_id, tag_id),
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id),
    FOREIGN KEY (tag_id) REFERENCES tag (id)
);