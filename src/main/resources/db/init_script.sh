#!/bin/bash

set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER postgres;
    CREATE DATABASE fleet;
    GRANT ALL PRIVILEGES ON DATABASE fleet TO postgres;
EOSQL

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "fleet" < /docker-entrypoint-initdb.d/create_tables_DB.sql
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "fleet" < /docker-entrypoint-initdb.d/create_tables_DB.sql
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "fleet" < /docker-entrypoint-initdb.d/populate_tracks_DB.sql
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "fleet" < /docker-entrypoint-initdb.d/populate_coordinates_DB.sql