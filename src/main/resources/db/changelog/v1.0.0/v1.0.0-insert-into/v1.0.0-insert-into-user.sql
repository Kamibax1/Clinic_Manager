-- Администраторы (2)
INSERT INTO Clinic_User (id_Clinic_User, nickname, email, password, created_at, updated_at, id_Role) VALUES
(1, 'admin', 'admin@medclinic.ru', '$2a$10$encrypted_admin_pass_1', '2023-01-15 08:00:00', '2026-04-01 10:00:00', 1),
(2, 'supervisor', 'supervisor@medclinic.ru', '$2a$10$encrypted_supervisor_pass', '2023-02-01 09:00:00', '2026-03-15 11:30:00', 1);

-- Врачи (8)
INSERT INTO Clinic_User (id_Clinic_User, nickname, email, password, created_at, updated_at, id_Role) VALUES
(3, 'dr_ivanov', 'ivanov@medclinic.ru', '$2a$10$encrypted_ivanov_pass', '2023-02-10 09:30:00', '2026-04-01 14:20:00', 2),
(4, 'dr_petrova', 'petrova@medclinic.ru', '$2a$10$encrypted_petrova_pass', '2023-03-05 11:15:00', '2026-03-28 09:45:00', 2),
(5, 'dr_sidorov', 'sidorov@medclinic.ru', '$2a$10$encrypted_sidorov_pass', '2023-04-12 10:00:00', '2026-03-20 16:30:00', 2),
(6, 'dr_kozlova', 'kozlova@medclinic.ru', '$2a$10$encrypted_kozlova_pass', '2023-05-20 14:45:00', '2026-04-05 11:15:00', 2),
(7, 'dr_smirnov', 'smirnov.doc@medclinic.ru', '$2a$10$encrypted_smirnov_doc_pass', '2023-06-01 08:30:00', '2026-03-10 15:00:00', 2),
(8, 'dr_fedorova', 'fedorova@medclinic.ru', '$2a$10$encrypted_fedorova_pass', '2023-07-10 10:00:00', '2025-10-25 13:45:00', 2),
(9, 'dr_nikolaev', 'nikolaev.doc@medclinic.ru', '$2a$10$encrypted_nikolaev_doc_pass', '2023-08-15 11:20:00', '2026-04-02 09:30:00', 2),
(10, 'dr_orlova', 'orlova@medclinic.ru', '$2a$10$encrypted_orlova_pass', '2023-09-20 14:00:00', '2025-11-05 16:15:00', 2);

-- Пациенты (30)
INSERT INTO Clinic_User (id_Clinic_User, nickname, email, password, created_at, updated_at, id_Role) VALUES
(11, 'petrov_ivan', 'petrov.patient@mail.ru', '$2a$10$encrypted_petrov_pass', '2023-06-10 12:00:00', '2026-04-10 09:00:00', 3),
(12, 'ivanova_maria', 'ivanova.patient@mail.ru', '$2a$10$encrypted_ivanova_pass', '2023-07-15 15:30:00', '2026-04-08 14:20:00', 3),
(13, 'smirnov_alex', 'smirnov@mail.ru', '$2a$10$encrypted_smirnov_pass', '2023-08-20 11:45:00', '2026-03-25 10:30:00', 3),
(14, 'kozlov_denis', 'kozlov@mail.ru', '$2a$10$encrypted_kozlov_pass', '2023-09-05 09:15:00', '2026-04-12 08:45:00', 3),
(15, 'morozova_elena', 'morozova@mail.ru', '$2a$10$encrypted_morozova_pass', '2023-10-10 16:20:00', '2026-04-01 13:10:00', 3),
(16, 'volkov_pavel', 'volkov@yandex.ru', '$2a$10$encrypted_volkov_pass', '2023-11-15 13:00:00', '2026-04-05 11:00:00', 3),
(17, 'sokolova_tanya', 'sokolova@yandex.ru', '$2a$10$encrypted_sokolova_pass', '2023-12-01 10:30:00', '2026-04-09 15:45:00', 3),
(18, 'mikhailov_andrey', 'mikhailov@yandex.ru', '$2a$10$encrypted_mikhailov_pass', '2024-01-20 14:15:00', '2026-04-11 09:30:00', 3),
(19, 'fedotova_olga', 'fedotova@gmail.com', '$2a$10$encrypted_fedotova_pass', '2024-02-15 11:00:00', '2026-04-07 12:20:00', 3),
(20, 'nikolaev_artem', 'nikolaev@gmail.com', '$2a$10$encrypted_nikolaev_pass', '2024-03-10 09:45:00', '2026-04-06 16:15:00', 3),
(21, 'popov_sergey', 'popov@gmail.com', '$2a$10$encrypted_popov_pass', '2024-01-05 13:30:00', '2026-03-20 10:00:00', 3),
(22, 'kuznetsova_anna', 'kuznetsova@mail.ru', '$2a$10$encrypted_kuznetsova_pass', '2024-02-20 10:15:00', '2026-04-03 11:45:00', 3),
(23, 'vasiliev_igor', 'vasiliev@mail.ru', '$2a$10$encrypted_vasiliev_pass', '2024-03-15 14:00:00', '2026-04-14 08:30:00', 3),
(24, 'novikova_katya', 'novikova@yandex.ru', '$2a$10$encrypted_novikova_pass', '2024-04-10 09:30:00', '2026-03-28 15:20:00', 3),
(25, 'zakharov_dima', 'zakharov@yandex.ru', '$2a$10$encrypted_zakharov_pass', '2024-05-05 11:45:00', '2026-04-01 12:10:00', 3),
(26, 'belyaeva_nata', 'belyaeva@gmail.com', '$2a$10$encrypted_belyaeva_pass', '2024-06-01 15:00:00', '2026-04-09 10:45:00', 3),
(27, 'tarasov_alexey', 'tarasov@gmail.com', '$2a$10$encrypted_tarasov_pass', '2024-07-10 10:30:00', '2026-03-25 14:00:00', 3),
(28, 'komarova_yulia', 'komarova@mail.ru', '$2a$10$encrypted_komarova_pass', '2024-08-15 13:15:00', '2026-04-04 09:15:00', 3),
(29, 'orlov_max', 'orlov@mail.ru', '$2a$10$encrypted_orlov_pass', '2024-09-20 08:45:00', '2026-04-13 11:30:00', 3),
(30, 'savelieva_marina', 'savelieva@yandex.ru', '$2a$10$encrypted_savelieva_pass', '2024-10-01 12:00:00', '2026-04-08 16:00:00', 3),
(31, 'frolov_andrey', 'frolov@yandex.ru', '$2a$10$encrypted_frolov_pass', '2024-10-15 14:30:00', '2026-04-02 10:15:00', 3),
(32, 'tikhomirova_elena', 'tikhomirova@gmail.com', '$2a$10$encrypted_tikhomirova_pass', '2024-11-01 09:00:00', '2026-04-05 13:45:00', 3),
(33, 'vinogradov_roman', 'vinogradov@gmail.com', '$2a$10$encrypted_vinogradov_pass', '2024-11-10 11:30:00', '2026-04-07 08:20:00', 3),
(34, 'zhukova_sveta', 'zhukova@mail.ru', '$2a$10$encrypted_zhukova_pass', '2024-11-15 15:15:00', '2026-04-01 14:30:00', 3),
(35, 'sorokin_valera', 'sorokin@mail.ru', '$2a$10$encrypted_sorokin_pass', '2024-11-20 10:45:00', '2026-04-10 09:00:00', 3),
(36, 'krylova_tanya', 'krylova@yandex.ru', '$2a$10$encrypted_krylova_pass', '2024-11-25 13:00:00', '2026-04-12 11:15:00', 3),
(37, 'vorobiev_kostya', 'vorobiev@yandex.ru', '$2a$10$encrypted_vorobiev_pass', '2024-12-01 08:30:00', '2026-04-14 15:45:00', 3),
(38, 'medvedeva_alina', 'medvedeva@gmail.com', '$2a$10$encrypted_medvedeva_pass', '2024-12-05 12:15:00', '2026-04-15 10:30:00', 3),
(39, 'zaicev_oleg', 'zaicev@gmail.com', '$2a$10$encrypted_zaicev_pass', '2024-12-10 14:00:00', '2026-04-16 09:00:00', 3),
(40, 'lebedeva_kristina', 'lebedeva@mail.ru', '$2a$10$encrypted_lebedeva_pass', '2024-12-12 09:45:00', '2026-04-16 13:20:00', 3);