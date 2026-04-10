package com.example.service;

import com.example.exception.NotFoundException;
import com.example.model.dto.AppointmentDTO;
import com.example.model.dto.AppointmentFullDTO;
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

    @Transactional
    public AppointmentDTO save (AppointmentDTO appointmentDTO) {
        return AppointmentDTO.fromEntity(appointmentRepository.save(convertToEntity(appointmentDTO)));
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> findAll() {
        return appointmentRepository.findAll().stream()
                .map(AppointmentDTO::fromEntity)
                .toList();
    }

    public List<AppointmentFullDTO> findAllFull() {
        return appointmentRepository.findAllFull().stream()
                .map(AppointmentFullDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<AppointmentDTO> findById(long id) {
        return appointmentRepository.findById(id)
                .map(AppointmentDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public Optional<AppointmentDTO> update(Long id, AppointmentDTO appointmentDTO) {
        if (!appointmentRepository.existsById(id)) {
            return Optional.empty();
        }
        AppointmentEntity entity = convertToEntity(appointmentDTO);
        entity.setId(id);
        return Optional.of(AppointmentDTO.fromEntity(appointmentRepository.save(entity)));
    }

    public void deleteById(long id) {
        appointmentRepository.deleteById(id);
    }

    public boolean existsById(long id) {
        return appointmentRepository.existsById(id);
    }

    private AppointmentEntity convertToEntity(AppointmentDTO dto) {
        StatusEntity status = statusRepository.findById(dto.getId_status())
                .orElseThrow(() -> new NotFoundException("Status not found with id: " + dto.getId_status()));

        PatientEntity patient = patientRepository.findById(dto.getId_patient())
                .orElseThrow(() -> new NotFoundException("Patient not found with id: " + dto.getId_patient()));

        DoctorEntity doctor = doctorRepository.findById(dto.getId_doctor())
                .orElseThrow(() -> new NotFoundException("Doctor not found with id: " + dto.getId_doctor()));

        return AppointmentDTO.toEntity(dto, status, patient, doctor);
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> findAllByDoctorId(long id) {
        List<AppointmentEntity> appointments = appointmentRepository.findAllByDoctorId(id);
        return appointments.stream()
                .map(AppointmentDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> findAllByStatus(StatusEnum status) {
        List<AppointmentEntity> appointments = appointmentRepository.findAllByStatusStatus(status);
        return appointments.stream()
                .map(AppointmentDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AppointmentFullDTO> findAllByDoctorName(String doctorName) {
        List<AppointmentEntity> appointments = appointmentRepository.findAllByDoctorName(doctorName);
        return appointments.stream()
                .map(AppointmentFullDTO::fromEntity)
                .toList();
    }
}
