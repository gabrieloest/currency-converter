--Account
INSERT INTO app_user (id, email, password, birth_day, city, country, street, zip,  date_created, deleted) VALUES (1, 'admin@email.com', '$2a$10$/fbSzfvEIb0CiZxhgmklIOMKKF3MSY1hLV3RtXF6OM/Y8/ps1PLJG' ,'1988-06-10', 'Natal', 'Brazil', 'Rua Professor Saturnino', '59015320', '2018-08-29 14:32:05.761', false);
--Authority
INSERT INTO authority (id, name, date_created, deleted) VALUES (1, 'ROLE_ADMIN', '2018-08-29', false);
INSERT INTO authority (id, name, date_created, deleted) VALUES (2, 'ROLE_USER', '2018-08-29', false);
--Authorities
INSERT INTO authorities (user_id, authority_id) VALUES (1, 1);
INSERT INTO authorities (user_id, authority_id) VALUES (1, 2);