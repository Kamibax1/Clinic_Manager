package com.example.service;

import com.example.model.dto.AppointmentFullInformationResponse;
import com.example.model.dto.AppointmentShortInfoResponse;
import com.example.model.dto.CreateAppointmentRequest;
import com.example.model.dto.CreateAppointmentResponse;
import com.example.model.entity.AppointmentEntity;
import com.example.model.entity.DoctorEntity;
import com.example.model.entity.PatientEntity;
import com.example.model.entity.StatusEntity;
import com.example.model.enums.StatusEnum;
import com.example.repository.AppointmentRepository;
import com.example.repository.DoctorRepository;
import com.example.repository.PatientRepository;
import com.example.repository.StatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final StatusRepository statusRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, StatusRepository statusRepository, PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.statusRepository = statusRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<AppointmentShortInfoResponse> findAllShortInfo() {
        return appointmentRepository.findAll().stream()
                .map(AppointmentShortInfoResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Optional<AppointmentFullInformationResponse> findFullInfoById(long id) {
        return appointmentRepository.findById(id)
                .map(AppointmentFullInformationResponse::fromEntity);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<AppointmentShortInfoResponse> findAllShortInfoByDoctorName(String doctorName) {
        return appointmentRepository.findAllShortInfoByDoctorName(doctorName).stream()
                .map(AppointmentShortInfoResponse::fromEntity)
                .toList();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Optional<AppointmentFullInformationResponse> updateStatus(long id, StatusEnum status) {
        return appointmentRepository.findById(id).map(appointment -> {
            StatusEntity newStatus = statusRepository.findByStatus(status);
            appointment.setStatus(newStatus);
            appointmentRepository.save(appointment);

            return AppointmentFullInformationResponse.fromEntity(appointment);
        });
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteById(long id) {
        appointmentRepository.deleteById(id);
    }

    public boolean existsById(long id) {
        return appointmentRepository.existsById(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public CreateAppointmentResponse createAppointment(CreateAppointmentRequest appointment) {
        StatusEntity status = statusRepository.findByStatus(StatusEnum.PENDING);

        PatientEntity patient = patientRepository.findById(appointment.getPatientId())
                .orElseThrow(() -> new RuntimeException("Пациент с ID " + appointment.getPatientId() + " не найден"));

        DoctorEntity doctor = doctorRepository.findById(appointment.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Доктор с ID " + appointment.getDoctorId() + " не найден"));

        AppointmentEntity entity = CreateAppointmentRequest.toEntity(appointment, status, patient, doctor);

        AppointmentEntity savedAppointment = appointmentRepository.save(entity);
        return CreateAppointmentResponse.fromEntity(savedAppointment);
    }
}
