-- Users

DROP TABLE IF EXISTS Users;
CREATE TABLE IF NOT EXISTS Users
(
    id           INTEGER                            NOT NULL ON CONFLICT ROLLBACK
        CONSTRAINT Users_pk
            PRIMARY KEY AUTOINCREMENT,
    login_name   TEXT                               NOT NULL,
    login_hash   TEXT COLLATE NOCASE                NOT NULL,
    display_name TEXT                               NOT NULL,
    create_time  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    role         TEXT                               NOT NULL
);

DROP INDEX IF EXISTS Users_id_uindex;
CREATE UNIQUE INDEX IF NOT EXISTS Users_id_uindex
    ON Users (id);

-- Locations

DROP TABLE IF EXISTS Locations;
CREATE TABLE IF NOT EXISTS Locations
(
    id          INTEGER                            NOT NULL ON CONFLICT ROLLBACK
        CONSTRAINT Locations_pk
            PRIMARY KEY AUTOINCREMENT,
    name        TEXT                               NOT NULL,
    level       INTEGER  DEFAULT 1                 NOT NULL,
    parent_id   INTEGER                            NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    delete_time DATETIME DEFAULT 0                 NOT NULL
);

DROP INDEX IF EXISTS Locations_id_name_index;
CREATE UNIQUE INDEX IF NOT EXISTS Locations_id_name_index
    ON Locations (id, name);

-- Things

DROP TABLE IF EXISTS Things;
CREATE TABLE IF NOT EXISTS Things
(
    id          INTEGER NOT NULL ON CONFLICT ROLLBACK
        CONSTRAINT Things_pk
            PRIMARY KEY AUTOINCREMENT,
    barcode     INTEGER,
    name        TEXT,
    price       INTEGER,
    expire_time TIME
);

DROP INDEX IF EXISTS Things_barcode_uindex;
CREATE UNIQUE INDEX IF NOT EXISTS Things_barcode_uindex
    ON Things (barcode);
