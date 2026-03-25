CREATE TABLE Role (
    id_Role BIGINT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE Clinic_User (
    id_Clinic_User BIGINT PRIMARY KEY,
    email VARCHAR(255),
    password VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    id_Role BIGINT NOT NULL REFERENCES Role (id_Role)
);

CREATE TABLE Doctor (
    id_Doctor BIGINT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    experience_years INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    id_Clinic_User BIGINT NOT NULL REFERENCES Clinic_User (id_Clinic_User)
);

CREATE TABLE Specialization (
    id_Specialization BIGINT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE doctor_specialization (
    id_Doctor BIGINT,
    id_Specialization BIGINT
);

CREATE TABLE Patient (
    id_Patient BIGINT PRIMARY KEY,
    full_name VARCHAR(255),
    date_of_birth DATE,
    gender VARCHAR(255),
    phone_number VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    id_Clinic_User BIGINT NOT NULL REFERENCES Clinic_User (id_Clinic_User)
);

CREATE TABLE Status (
    id_Status BIGINT PRIMARY KEY,
    status VARCHAR(255)
);

CREATE TABLE Appointment (
    id_Appointment BIGINT PRIMARY KEY,
    date_time TIMESTAMP,
    symptoms VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    id_Status BIGINT NOT NULL REFERENCES Status (id_Status),
    id_Doctor BIGINT NOT NULL REFERENCES Doctor (id_Doctor),
    id_Patient BIGINT NOT NULL REFERENCES Patient (id_Patient)
);
