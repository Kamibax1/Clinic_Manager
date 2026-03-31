package com.example.service.main;

import com.example.model.dto.AppointmentDTO;
import com.example.model.dto.DoctorDTO;
import com.example.model.dto.PatientDTO;
import com.example.model.entity.AppointmentEntity;
import com.example.model.entity.PatientEntity;
import com.example.model.enums.StatusEnum;
import com.example.repository.AppointmentRepository;
import com.example.repository.DoctorRepository;
import com.example.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MainDoctorService {
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;

    public MainDoctorService(PatientRepository patientRepository,  AppointmentRepository appointmentRepository, DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
    }

    public Optional<List<PatientDTO>> findPatientsByPartFullName(String partFullName){
        Optional<List<PatientEntity>> patientEntity = patientRepository.searchByFullName(partFullName);
        return Optional.of(patientEntity.stream()
                .flatMap(List::stream)
                .map(PatientDTO::fromEntity)
                .toList());
    }

    public List<PatientDTO> findAllPatients(){
        return patientRepository.findAll().stream()
                .map(PatientDTO::fromEntity)
                .toList();
    }

    public Optional<List<AppointmentDTO>> findAllAppointmentByDoctorId(long id){
        Optional<List<AppointmentEntity>> appointments = appointmentRepository.findAllAppointmentByDoctorId(id);
        return Optional.of(appointments.stream()
                .flatMap(List::stream)
                .map(AppointmentDTO::fromEntity)
                .toList());
    }

    public Optional<List<AppointmentDTO>> findAllAppointmentByStatus(StatusEnum status){
        Optional<List<AppointmentEntity>> appointments = appointmentRepository.findAllAppointmentByStatus(status);
        return Optional.of(appointments.stream()
                .flatMap(List::stream)
                .map(AppointmentDTO::fromEntity)
                .toList());
    }

    public List<DoctorDTO> findAllDoctors(){
        return doctorRepository.findAll().stream()
                .map(DoctorDTO::fromEntity)
                .toList();
    }
}
