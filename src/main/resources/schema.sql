DROP SCHEMA IF EXISTS zooplus CASCADE;
CREATE SCHEMA zooplus;

DROP TABLE IF EXISTS zooplus.app_user CASCADE;
CREATE TABLE zooplus.app_user
(
  id bigint NOT NULL,
  date_created timestamp without time zone NOT NULL,
  deleted boolean NOT NULL,
  email character varying(255) NOT NULL,
  password character varying(255) NOT NULL,
  birth_day date NOT NULL,
  city character varying(255),
  country character varying(255),
  street character varying(255),
  zip character varying(255),
  CONSTRAINT app_user_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS zooplus.exchange CASCADE;
CREATE TABLE zooplus.exchange
(
  id bigint NOT NULL,
  amount numeric(50,14) NOT NULL,
  currency character varying(255) NOT NULL,
  date date NOT NULL,
  date_created timestamp without time zone NOT NULL,
  deleted boolean NOT NULL,
  user_id bigint,
  CONSTRAINT exchange_pkey PRIMARY KEY (id),
  CONSTRAINT fkl5rd6k75p9dr4f3fkvgimwk11 FOREIGN KEY (user_id)
      REFERENCES zooplus.app_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

DROP TABLE IF EXISTS zooplus.rate CASCADE;
CREATE TABLE zooplus.rate
(
  id bigint NOT NULL,
  amount numeric(50,14) NOT NULL,
  currency character varying(255) NOT NULL,
  id_exchange bigint,
  CONSTRAINT rate_pkey PRIMARY KEY (id),
  CONSTRAINT fkfer8n7xhawy6h6wot92blmme0 FOREIGN KEY (id_exchange)
      REFERENCES zooplus.exchange (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

DROP TABLE IF EXISTS zooplus.authority CASCADE;
CREATE TABLE zooplus.authority
(
  id bigint NOT NULL,
  date_created timestamp without time zone NOT NULL,
  deleted boolean NOT NULL,
  name character varying(50) NOT NULL,
  CONSTRAINT authority_pkey PRIMARY KEY (id),
  CONSTRAINT uk_jdeu5vgpb8k5ptsqhrvamuad2 UNIQUE (name)
);

DROP TABLE IF EXISTS zooplus.authorities CASCADE;
CREATE TABLE zooplus.authorities
(
  user_id bigint NOT NULL,
  authority_id bigint NOT NULL,
  CONSTRAINT authorities_pkey PRIMARY KEY (user_id, authority_id),
  CONSTRAINT fkbmqr3ysvq0p6c684y85faka9j FOREIGN KEY (user_id)
      REFERENCES zooplus.app_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkjo0ihohj7bdcq0wip1di0p6hm FOREIGN KEY (authority_id)
      REFERENCES zooplus.authority (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
