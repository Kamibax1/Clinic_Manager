SELECT setval('role_id_role_seq', (SELECT MAX(id_Role) FROM Role));
SELECT setval('clinic_user_id_clinic_user_seq', (SELECT MAX(id_Clinic_User) FROM Clinic_User));
SELECT setval('doctor_id_doctor_seq', (SELECT MAX(id_Doctor) FROM Doctor));
SELECT setval('patient_id_patient_seq', (SELECT MAX(id_Patient) FROM Patient));
SELECT setval('specialization_id_specialization_seq', (SELECT MAX(id_Specialization) FROM Specialization));
SELECT setval('status_id_status_seq', (SELECT MAX(id_Status) FROM Status));
SELECT setval('appointment_id_appointment_seq', (SELECT MAX(id_Appointment) FROM Appointment));