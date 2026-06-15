#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 -U "$POSTGRES_USER" -d "$POSTGRES_DB" <<-EOSQL
    CREATE DATABASE clientdb;
    CREATE DATABASE carddb;
EOSQL

psql -v ON_ERROR_STOP=1 -U "$POSTGRES_USER" -d clientdb -f /ddl/client.sql
psql -v ON_ERROR_STOP=1 -U "$POSTGRES_USER" -d carddb -f /ddl/card.sql
