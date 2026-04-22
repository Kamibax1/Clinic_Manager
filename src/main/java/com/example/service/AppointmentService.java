package com.example.service;

import com.example.exception.AccessDeniedException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.dto.appointment.request.UpdateAppointmentStatusRequest;
import com.example.model.dto.appointment.response.*;
import com.example.model.dto.appointment.request.CreateAppointmentRequest;
import com.example.model.entity.*;
import com.example.model.enums.StatusEnum;
import com.example.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public CreateAppointmentResponse createAppointment(CreateAppointmentRequest appointment) {
        log.info("Creating appointment for patient ID: {}, doctor ID: {}", appointment.getPatientId(), appointment.getDoctorId());
        StatusEntity status = statusRepository.findByStatus(StatusEnum.PENDING);
        if (status == null) {
            throw new ResourceNotFoundException("Status", "name", StatusEnum.PENDING);
        }

        PatientEntity patient = patientRepository.findById(appointment.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", appointment.getPatientId()));
        DoctorEntity doctor = doctorRepository.findById(appointment.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", appointment.getDoctorId()));

        AppointmentEntity entity = CreateAppointmentRequest.toEntity(appointment, status, patient, doctor);
        AppointmentEntity savedAppointment = appointmentRepository.save(entity);

        log.info("Appointment created successfully with ID: {}", savedAppointment.getId());
        return CreateAppointmentResponse.fromEntity(savedAppointment);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<AppointmentShortInfoResponse> findAllShortInfo() {
        return appointmentRepository.findAll().stream()
                .map(AppointmentShortInfoResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<AppointmentShortInfoResponse> findAllShortInfoByPatientId(long patientId) {
        return appointmentRepository.findAllByPatientId(patientId).stream()
                .map(AppointmentShortInfoResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<AppointmentShortInfoResponse> findAllShortInfoByDoctorId(long id) {
        return appointmentRepository.findAllByDoctorId(id).stream()
                .map(AppointmentShortInfoResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public AppointmentFullInformationResponse findFullInfoById(long id) {
        return appointmentRepository.findById(id)
                .map(AppointmentFullInformationResponse::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", id));
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<AppointmentShortInfoResponse> findAllShortInfoByDoctorName(String doctorName) {
        return appointmentRepository.findAllShortInfoByDoctorName(doctorName).stream()
                .map(AppointmentShortInfoResponse::fromEntity)
                .toList();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public AppointmentFullInformationResponse updateStatus(long id, StatusEnum status) {
        log.info("Updating appointment ID: {} to status: {}", id, status);
        AppointmentEntity appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", id));
        StatusEntity newStatus = statusRepository.findByStatus(status);
        if (newStatus == null) {
            throw new ResourceNotFoundException("Status", "name", status);
        }
        appointment.setStatus(newStatus);
        appointmentRepository.save(appointment);

        log.info("Appointment ID: {} status updated to: {}", id, status);
        return AppointmentFullInformationResponse.fromEntity(appointment);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public AppointmentFullInformationResponse updateDoctorAppointmentStatus(long appointmentId, UpdateAppointmentStatusRequest request) {
        log.info("Doctor ID: {} updating appointment ID: {} to status: {}", request.getDoctorId(), appointmentId, request.getStatus());
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", appointmentId));
        if (appointment.getDoctor().getId() != request.getDoctorId()) {
            log.warn("Doctor ID: {} attempted to update appointment ID: {} belonging to doctor ID: {}",
                    request.getDoctorId(), appointmentId, appointment.getDoctor().getId());
            throw new AccessDeniedException("Appointment", appointmentId, "DOCTOR");
        }

        StatusEntity newStatus = statusRepository.findByStatus(request.getStatus());
        if (newStatus == null) {
            throw new ResourceNotFoundException("Status", "name", request.getStatus());
        }

        appointment.setStatus(newStatus);
        appointmentRepository.save(appointment);

        log.info("Appointment ID: {} status updated to: {} by doctor ID: {}", appointmentId, request.getStatus(), request.getDoctorId());
        return AppointmentFullInformationResponse.fromEntity(appointment);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteById(long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment", id);
        }
        appointmentRepository.deleteById(id);
        log.info("Appointment ID: {} deleted successfully", id);
    }

    public boolean existsById(long id) {
        return appointmentRepository.existsById(id);
    }
}
