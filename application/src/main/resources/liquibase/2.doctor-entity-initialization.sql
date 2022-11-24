CREATE TABLE doctor
(
    id                  TEXT        NOT NULL PRIMARY KEY,
    creation_timestamp  TIMESTAMPTZ NOT NULL,
    full_name           JSON        NOT NULL,
    specialization_name TEXT        NOT NULL,
    organization_id     TEXT        NOT NULL
);

CREATE INDEX ON doctor (creation_timestamp);
