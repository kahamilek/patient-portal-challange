CREATE TABLE patient
(
    id              TEXT   NOT NULL PRIMARY KEY,
    sequence_id     SERIAL NOT NULL,
    full_name       JSON   NOT NULL,
    address         JSON   NOT NULL,
    organization_id TEXT   NOT NULL
);

CREATE INDEX ON patient (sequence_id);

CREATE TABLE organization
(
    id   TEXT NOT NULL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE INDEX ON organization (name);
