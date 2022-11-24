CREATE TABLE doctors_appointment
(
    id                     TEXT        NOT NULL PRIMARY KEY,
    creation_timestamp     TIMESTAMPTZ NOT NULL,
    date                   DATE        NOT NULL,
    time                   TIME        NOT NULL,
    place                  TEXT        NOT NULL,
    attending_physician_id TEXT        NOT NULL,
    patient_id             TEXT        NOT NULL
);

CREATE INDEX ON doctors_appointment (creation_timestamp);
