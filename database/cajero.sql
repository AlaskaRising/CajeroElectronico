CREATE SCHEMA IF NOT EXISTS banco;

CREATE TABLE IF NOT EXISTS banco.tipo_billete(
    id_tipo_billete serial NOT NULL,
    denominacion numeric(7,0) NOT NULL,
    descripcion character varying(100),
    activo boolean NOT NULL,
    CONSTRAINT pk_tipo_billete PRIMARY KEY (id_tipo_billete),
    CONSTRAINT uq_denominacion unique (denominacion)
);

CREATE TABLE IF NOT EXISTS banco.cajero(
    id_cajero serial NOT NULL,
    nombre character varying(100),
    ubicacion character varying(100),
    activo boolean NOT NULL,
    CONSTRAINT pk_cajero PRIMARY KEY (id_cajero)
);

CREATE TABLE IF NOT EXISTS banco.tipo_billete_cajero(
    id_tipo_billete_cajero serial NOT NULL,
    id_cajero integer NOT NULL,
    id_tipo_billete integer NOT NULL,
    cantidad integer NOT NULL,
    activo boolean NOT NULL,
    CONSTRAINT pk_tipo_billete_cajero PRIMARY KEY (id_tipo_billete_cajero),
    CONSTRAINT fk_cajero_tipo_billete_cajero FOREIGN KEY (id_cajero) REFERENCES banco.cajero(id_cajero),
    CONSTRAINT fk_tipo_billete_tipo_billete_cajero FOREIGN KEY (id_tipo_billete) REFERENCES banco.tipo_billete(id_tipo_billete),
    CONSTRAINT ck_cantidad_positiva check (cantidad>=0),
    CONSTRAINT uq_cajero_tipo_billete unique (id_cajero, id_tipo_billete)
);

INSERT INTO banco.cajero (id_cajero, nombre, activo) VALUES (1, 'Cajero Centro', TRUE);

INSERT INTO banco.tipo_billete (id_tipo_billete, denominacion, activo) VALUES (1, 50000, TRUE);
INSERT INTO banco.tipo_billete (id_tipo_billete, denominacion, activo) VALUES (2, 20000, TRUE);
INSERT INTO banco.tipo_billete (id_tipo_billete, denominacion, activo) VALUES (3, 10000, TRUE);
INSERT INTO banco.tipo_billete (id_tipo_billete, denominacion, activo) VALUES (4, 5000, TRUE);
INSERT INTO banco.tipo_billete (id_tipo_billete, denominacion, activo) VALUES (5, 2000, TRUE);
INSERT INTO banco.tipo_billete (id_tipo_billete, denominacion, activo) VALUES (6, 1000, TRUE);

INSERT INTO banco.tipo_billete_cajero (id_tipo_billete_cajero, id_cajero, id_tipo_billete, cantidad, activo) VALUES (1, 1, 1, 10, TRUE);
INSERT INTO banco.tipo_billete_cajero (id_tipo_billete_cajero, id_cajero, id_tipo_billete, cantidad, activo) VALUES (2, 1, 2, 10, TRUE);
INSERT INTO banco.tipo_billete_cajero (id_tipo_billete_cajero, id_cajero, id_tipo_billete, cantidad, activo) VALUES (3, 1, 3, 10, TRUE);
INSERT INTO banco.tipo_billete_cajero (id_tipo_billete_cajero, id_cajero, id_tipo_billete, cantidad, activo) VALUES (4, 1, 4, 10, TRUE);
INSERT INTO banco.tipo_billete_cajero (id_tipo_billete_cajero, id_cajero, id_tipo_billete, cantidad, activo) VALUES (5, 1, 5, 10, TRUE);
INSERT INTO banco.tipo_billete_cajero (id_tipo_billete_cajero, id_cajero, id_tipo_billete, cantidad, activo) VALUES (6, 1, 6, 10, TRUE);
