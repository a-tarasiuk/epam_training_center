INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('certificate 1', 'description for gift certificate 1', 1.1, 1, '2021-10-01 17:05:53.889',
        '2021-10-01 17:05:53.889'),
       ('certificate 2', 'description for gift certificate 2', 2.2, 2, '2021-10-02 17:05:53.889',
        '2021-10-02 17:05:53.889'),
       ('certificate 3', 'description for gift certificate 3', 3.3, 3, '2021-10-03 17:05:53.889',
        '2021-10-03 17:05:53.889');

INSERT INTO tag (name)
VALUES ('epam'),
       ('gift'),
       ('gym');

INSERT INTO gift_certificate_to_tag_relation (gift_certificate_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 2),
       (3, 3);