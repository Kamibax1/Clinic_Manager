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

    public AppointmentDTO save (AppointmentDTO appointmentDTO) {
        return AppointmentDTO.fromEntity(appointmentRepository.save(saveOrUpdate(appointmentDTO)));
    }

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

    public Optional<AppointmentDTO> findById(long id) {
        return appointmentRepository.findById(id)
                .map(AppointmentDTO::fromEntity);
    }

    public Optional<AppointmentDTO> update(Long id, AppointmentDTO appointmentDTO) {
        if (!appointmentRepository.existsById(id)) {
            return Optional.empty();
        }
        AppointmentEntity entity = saveOrUpdate(appointmentDTO);
        entity.setId(id);
        return Optional.of(AppointmentDTO.fromEntity(appointmentRepository.save(entity)));
    }

    public void deleteById(long id) {
        appointmentRepository.deleteById(id);
    }

    public boolean existsById(long id) {
        return appointmentRepository.existsById(id);
    }

    public AppointmentEntity saveOrUpdate(AppointmentDTO appointmentDTO) {
        Optional<StatusEntity> statusEntity =  statusRepository.findById(appointmentDTO.getId_status());
        Optional<PatientEntity> patientEntity = patientRepository.findById(appointmentDTO.getId_patient());
        Optional<DoctorEntity> doctorEntity = doctorRepository.findById(appointmentDTO.getId_doctor());

        if(statusEntity.isEmpty()){
            throw new NotFoundException("Status not found");
        }
        if(patientEntity.isEmpty()){
            throw new NotFoundException("Patient not found");
        }
        if(doctorEntity.isEmpty()){
            throw new NotFoundException("Doctor not found");
        }

        else {
            return AppointmentDTO.toEntity(
                    appointmentDTO,
                    statusEntity.get(),
                    patientEntity.get(),
                    doctorEntity.get()
            );
        }
    }

    public Optional<List<AppointmentDTO>> findAllByDoctorId(long id){
        Optional<List<AppointmentEntity>> appointments = appointmentRepository.findAllAppointmentByDoctorId(id);
        return Optional.of(appointments.stream()
                .flatMap(List::stream)
                .map(AppointmentDTO::fromEntity)
                .toList());
    }

    public Optional<List<AppointmentDTO>> findAllByStatus(StatusEnum status){
        Optional<List<AppointmentEntity>> appointments = appointmentRepository.findAllAppointmentByStatus(status);
        return Optional.of(appointments.stream()
                .flatMap(List::stream)
                .map(AppointmentDTO::fromEntity)
                .toList());
    }
}
