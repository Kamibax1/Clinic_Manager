package com.example.service;

import com.example.model.dto.DoctorsDTO;
import com.example.model.entity.SpecializationEntity;
import com.example.repository.MainRepository;
import com.example.repository.SpecializationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {

    private final MainRepository mainRepository;
    private final SpecializationRepository specializationRepository;

    public MainService(MainRepository mainRepository, SpecializationRepository specializationRepository) {
        this.mainRepository = mainRepository;
        this.specializationRepository = specializationRepository;
    }

    public List<DoctorsDTO> findAllDoctors() {
        return mainRepository.findAll().stream()
                .map(DoctorsDTO::fromEntity)
                .toList();
    }

    public List<String> findAllSpecializations() {
        return specializationRepository.findAll().stream()
                .map(SpecializationEntity::getName)
                .toList();
    }
}
