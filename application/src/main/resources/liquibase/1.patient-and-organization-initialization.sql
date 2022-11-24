CREATE TABLE patient
(
    id                 TEXT        NOT NULL PRIMARY KEY,
    creation_timestamp TIMESTAMPTZ NOT NULL,
    full_name          JSON        NOT NULL,
    address            JSON        NOT NULL,
    organization_id    TEXT        NOT NULL
);

CREATE INDEX ON patient (creation_timestamp);

CREATE TABLE organization
(
    id   TEXT NOT NULL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE UNIQUE INDEX INDEX ON organization (name);
