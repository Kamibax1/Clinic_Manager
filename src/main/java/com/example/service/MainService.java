package com.example.service;

import com.example.model.dto.DoctorDTO;
import com.example.model.entity.SpecializationEntity;
import com.example.repository.DoctorRepository;
import com.example.repository.SpecializationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {

    private final DoctorRepository doctorRepository;
    private final SpecializationRepository specializationRepository;

    public MainService(DoctorRepository doctorRepository, SpecializationRepository specializationRepository) {
        this.doctorRepository = doctorRepository;
        this.specializationRepository = specializationRepository;
    }

    public List<DoctorDTO> findAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(DoctorDTO::fromEntity)
                .toList();
    }

    public List<String> findAllSpecializations() {
        return specializationRepository.findAll().stream()
                .map(SpecializationEntity::getName)
                .toList();
    }
}
