package com.example.service;

import com.example.model.dto.HomeStatsResponse;
import com.example.model.enums.StatusEnum;
import com.example.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class HomeStatsService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public HomeStatsService(AppointmentRepository appointmentRepository, PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public HomeStatsResponse getHomeStats() {
        return new HomeStatsResponse(
                appointmentRepository.findAllByDate(LocalDate.now()).size(),
                patientRepository.findAll().size(),
                doctorRepository.findAll().size(),
                appointmentRepository.findAllByStatusStatus(StatusEnum.COMPLETED).size()
        );
    }
}
