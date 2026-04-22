package com.example.service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.dto.ProfileStatsResponse;
import com.example.model.entity.DoctorEntity;
import com.example.model.entity.PatientEntity;
import com.example.model.enums.StatusEnum;
import com.example.repository.AppointmentRepository;
import com.example.repository.DoctorRepository;
import com.example.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ProfileStatsService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public ProfileStatsService(AppointmentRepository appointmentRepository, PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public ProfileStatsResponse getProfileStatsPatient(long patientId) {
        log.debug("Getting profile stats for patient ID: {}", patientId);
        PatientEntity patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", patientId));

        ProfileStatsResponse response = new ProfileStatsResponse();
        response.setCountAppointment(appointmentRepository.countByPatientId(patientId));
        response.setDateOfRegistration(patient.getUser().getCreatedAt().toLocalDateTime().toLocalDate());
        response.setCountCurrentAppointment(
                appointmentRepository.countByPatientIdAndStatusStatusIn(
                        patientId,
                        List.of(StatusEnum.PENDING, StatusEnum.IN_PROGRESS, StatusEnum.SCHEDULED)
                )
        );
        return response;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public ProfileStatsResponse getProfileStatsDoctor(long doctorId) {
        log.debug("Getting profile stats for doctor ID: {}", doctorId);
        DoctorEntity doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", doctorId));

        ProfileStatsResponse response = new ProfileStatsResponse();
        response.setCountAppointment(appointmentRepository.countByDoctorId(doctorId));
        response.setDateOfRegistration(doctor.getUser().getCreatedAt().toLocalDateTime().toLocalDate());
        response.setCountCurrentAppointment(
                appointmentRepository.countByDoctorIdAndStatusStatusIn(
                        doctorId,
                        List.of(StatusEnum.PENDING, StatusEnum.IN_PROGRESS, StatusEnum.SCHEDULED)
                )
        );
        return response;
    }
}
