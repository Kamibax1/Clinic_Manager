package com.example.service;

import com.example.model.dto.SpecializationDTO;
import com.example.repository.SpecializationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SpecializationService {
    private final SpecializationRepository specializationRepository;
    public SpecializationService(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }

    @Transactional(readOnly = true)
    public List<SpecializationDTO> findAllSpecializations() {
        return specializationRepository.findAll().stream()
                .map(SpecializationDTO::fromEntity)
                .toList();
    }
}
