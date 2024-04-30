DROP TABLE IF EXISTS vehicles CASCADE;
DROP TABLE IF EXISTS drivers CASCADE;
DROP TABLE IF EXISTS enterprises CASCADE;
DROP TABLE IF EXISTS vehicle_models CASCADE;
DROP TABLE IF EXISTS managers CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS enterprises_managers CASCADE;
DROP TABLE IF EXISTS tracks CASCADE;
DROP TABLE IF EXISTS vehicle_coordinates CASCADE;


DROP SEQUENCE IF EXISTS global_seq;

--CREATE EXTENSION postgis;
CREATE SEQUENCE global_seq START WITH 500000;


CREATE TABLE enterprises
(
    id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name      VARCHAR NOT NULL,
    city      VARCHAR NOT NULL,
    time_zone VARCHAR NOT NULL
);
CREATE UNIQUE INDEX enterprises_unique_name_idx ON enterprises (name);


CREATE TABLE vehicle_models
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    brand         VARCHAR NOT NULL,
    name          VARCHAR NOT NULL,
    vehicle_type  VARCHAR NOT NULL,
    num_seats     INTEGER NOT NULL,
    engine_type   VARCHAR NOT NULL,
    load_capacity INT     NOT NULL
);


CREATE TABLE vehicles
(
    id              INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    model_id        INTEGER NOT NULL,
    vin             VARCHAR NOT NULL,
    color           VARCHAR NOT NULL,
    cost_usd        DECIMAL NOT NULL,
    mileage         INTEGER NOT NULL,
    production_year INTEGER NOT NULL,
    enterprise_id   INTEGER NOT NULL,
    purchase_date   TIMESTAMP,
    FOREIGN KEY (model_id) REFERENCES vehicle_models (id) ON DELETE CASCADE,
    FOREIGN KEY (enterprise_id) REFERENCES enterprises (id) ON DELETE CASCADE
);

CREATE TABLE drivers
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    first_name    VARCHAR NOT NULL,
    second_name   VARCHAR NOT NULL,
    salary_usd    DECIMAL NOT NULL,
    experience    INTEGER NOT NULL,
    enterprise_id INTEGER NOT NULL,
    vehicle_id    INTEGER,
    is_active     BOOLEAN NOT NULL    DEFAULT FALSE,
    FOREIGN KEY (enterprise_id) REFERENCES enterprises (id) ON DELETE CASCADE,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles (id) ON DELETE CASCADE
);


CREATE TABLE managers
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    login       VARCHAR NOT NULL,
    password    VARCHAR NOT NULL,
    first_name  VARCHAR NOT NULL,
    second_name VARCHAR NOT NULL
);

CREATE TABLE enterprises_managers
(
    enterprise_id INTEGER NOT NULL,
    manager_id    INTEGER NOT NULL,
    CONSTRAINT enterprises_managers_idx UNIQUE (enterprise_id, manager_id),
    FOREIGN KEY (enterprise_id) REFERENCES enterprises (id) ON DELETE CASCADE,
    FOREIGN KEY (manager_id) REFERENCES managers (id) ON DELETE CASCADE
);

CREATE TABLE users
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    login       VARCHAR NOT NULL,
    password    VARCHAR NOT NULL,
    first_name  VARCHAR NOT NULL,
    second_name VARCHAR NOT NULL
);


CREATE UNIQUE INDEX vehicle_unique_vin_idx ON vehicles (vin);

CREATE TABLE tracks
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    vehicle_id INTEGER not null,
    started    TIMESTAMP,
    finished   TIMESTAMP,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles (id) ON DELETE CASCADE
);

CREATE TABLE vehicle_coordinates
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    track_id   INTEGER               not null,
    vehicle_id INTEGER               not null,
    lat        FLOAT                 NOT NULL,
    lon        FLOAT                 NOT NULL,
    position   geometry(POINT, 4326) NOT NULL,
    visited    TIMESTAMP             NOT NULL,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles (id) ON DELETE CASCADE,
    FOREIGN KEY (track_id) REFERENCES tracks (id) ON DELETE CASCADE
);