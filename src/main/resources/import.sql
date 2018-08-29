--Account
--INSERT INTO user (id, email, enabled, password_hash, birth_day, city, country, street, zip, created_by, created_date) VALUES (1, 'admin', 1, '$2a$10$l/lHKoFTFdzfVyyZ9oIDPu3voNZZLu/9qi.8BhDMHRcaFmetHx/UO' ,'2013-09-29', 'city', 'country', 'street', 'zip', 'system', '2013-09-29 22:00:00');
--INSERT INTO user (id, email, enabled, password_hash, birth_day, city, country, street, zip, created_by, created_date) VALUES (2, 'user', 1, '$2a$10$muOJKIPqChcNnFu8nduPJONfT3uSsTIoQRstlWFXYJ3c1Yln0kzt.' ,'2013-09-29', 'city', 'country', 'street', 'zip', 'system', '2013-09-29 22:00:00');
--Authority
INSERT INTO authority (id, pname) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authority (id, pname) VALUES (2, 'ROLE_USER');
--Authorities
--INSERT INTO authorities (user_id, authority_id) VALUES (1, 1);
--INSERT INTO authorities (user_id, authority_id) VALUES (2, 2);
