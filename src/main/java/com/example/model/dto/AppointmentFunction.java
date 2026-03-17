package com.example.model.dto;

import com.example.exception.NotFoundException;
import com.example.model.entity.AppointmentEntity;
import com.example.model.entity.DoctorEntity;
import com.example.model.entity.PatientEntity;
import com.example.model.entity.StatusEntity;
import com.example.repository.DoctorRepository;
import com.example.repository.PatientRepository;
import com.example.repository.StatusRepository;

import java.util.Optional;

public class AppointmentFunction {

    public static AppointmentEntity saveOrUpdate(AppointmentDTO appointmentDTO, StatusRepository statusRepository, PatientRepository patientRepository, DoctorRepository doctorRepository) {
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
            return AppointmentDTO.fromDTO(
                    appointmentDTO,
                    statusEntity.get(),
                    patientEntity.get(),
                    doctorEntity.get()
            );
        }
    }
}
