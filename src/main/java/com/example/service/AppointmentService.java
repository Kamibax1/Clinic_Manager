package com.example.service;

import com.example.model.dto.AppointmentFullInformationResponse;
import com.example.model.dto.AppointmentShortInfoResponse;
import com.example.model.dto.CreateAppointmentRequest;
import com.example.model.entity.StatusEntity;
import com.example.model.enums.StatusEnum;
import com.example.repository.AppointmentRepository;
import com.example.repository.StatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final StatusRepository statusRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, StatusRepository statusRepository) {
        this.appointmentRepository = appointmentRepository;
        this.statusRepository = statusRepository;
    }

    @Transactional(readOnly = true)
    public List<AppointmentShortInfoResponse> findAllShortInfo() {
        return appointmentRepository.findAll().stream()
                .map(AppointmentShortInfoResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<AppointmentFullInformationResponse> findFullInfoById(long id) {
        return appointmentRepository.findById(id)
                .map(AppointmentFullInformationResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public List<AppointmentShortInfoResponse> findAllShortInfoByDoctorName(String doctorName) {
        return appointmentRepository.findAllShortInfoByDoctorName(doctorName).stream()
                .map(AppointmentShortInfoResponse::fromEntity)
                .toList();
    }

    @Transactional
    public Optional<AppointmentFullInformationResponse> updateStatus(long id, StatusEnum status) {
        return appointmentRepository.findById(id).map(appointment -> {
            StatusEntity newStatus = statusRepository.findByStatus(status);
            appointment.setStatus(newStatus);
            appointmentRepository.save(appointment);

            return AppointmentFullInformationResponse.fromEntity(appointment);
        });
    }

    public void deleteById(long id) {
        appointmentRepository.deleteById(id);
    }

    public boolean existsById(long id) {
        return appointmentRepository.existsById(id);
    }

    @Transactional
    public CreateAppointmentRequest createAppointment(CreateAppointmentRequest appointment) {
        return CreateAppointmentRequest.fromEntity(appointmentRepository.save(CreateAppointmentRequest.toEntity(appointment)));
    }
}
