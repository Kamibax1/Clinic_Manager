package com.example.service;

import com.example.model.dto.AppointmentDTO;
import com.example.model.dto.AppointmentFunction;
import com.example.model.entity.AppointmentEntity;
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
        return AppointmentDTO.fromEntity(appointmentRepository
                .save(AppointmentFunction
                        .saveOrUpdate(
                                appointmentDTO,
                                statusRepository,
                                patientRepository,
                                doctorRepository
                        )
                )
        );
    }

    public List<AppointmentDTO> findAll() {
        return appointmentRepository.findAll().stream()
                .map(AppointmentDTO::fromEntity)
                .toList();
    }

    public Optional<AppointmentDTO> findById(long id) {
        Optional<AppointmentEntity> appointmentEntity = appointmentRepository.findById(id);
        return appointmentEntity.map(AppointmentDTO::fromEntity);
    }

    public Optional<AppointmentDTO> update(long id, AppointmentDTO  appointmentDTO) {
            Optional<AppointmentEntity> appointmentEntity = appointmentRepository.findById(id);
            return appointmentEntity.map(entity -> AppointmentDTO
                    .fromEntity(AppointmentFunction
                            .saveOrUpdate(
                                    appointmentDTO,
                                    statusRepository,
                                    patientRepository,
                                    doctorRepository
                            )
                    )
            );
    }

}
