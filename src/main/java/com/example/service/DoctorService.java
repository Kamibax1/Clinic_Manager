package com.example.service;

import com.example.model.dto.DoctorDTO;
import com.example.model.entity.DoctorEntity;
import com.example.model.entity.SpecializationEntity;
import com.example.repository.DoctorRepository;
import com.example.repository.SpecializationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final SpecializationRepository specializationRepository;

    public DoctorService(DoctorRepository doctorRepository, SpecializationRepository specializationRepository) {
        this.doctorRepository = doctorRepository;
        this.specializationRepository = specializationRepository;
    }

    public List<DoctorDTO> findAll() {
        return doctorRepository.findAll().stream()
                .map(DoctorDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<DoctorDTO> findById(long id) {
        Optional<DoctorEntity> doctorEntity = doctorRepository.findById(id);
        return doctorEntity.map(DoctorDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public List<DoctorDTO> searchByName(String partName){
        List<DoctorEntity> doctors = doctorRepository.searchByName(partName);
        return doctors.stream()
                .map(DoctorDTO::fromEntity)
                .toList();
    }

    public List<String> findAllSpecializations() {
        return specializationRepository.findAll().stream()
                .map(SpecializationEntity::getName)
                .toList();
    }


}
