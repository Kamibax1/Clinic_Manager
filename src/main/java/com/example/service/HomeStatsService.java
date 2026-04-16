package com.example.service;

import com.example.model.dto.HomeStatsResponse;
import com.example.repository.AppointmentRepository;
import com.example.repository.DoctorRepository;
import com.example.repository.PatientRepository;
import com.example.repository.StatusRepository;
import org.springframework.stereotype.Service;

@Service
public class HomeStatsService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final StatusRepository statusRepository;

    public HomeStatsService(AppointmentRepository appointmentRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, StatusRepository statusRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.statusRepository = statusRepository;
    }

    public HomeStatsResponse

}
