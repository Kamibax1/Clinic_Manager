package com.example.service;

import com.example.model.dto.profile.PatientDataDTO;
import com.example.model.entity.PatientEntity;
import com.example.repository.PatientDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientDataService {

    private final PatientDataRepository patientDataRepository;

    public PatientDataService(PatientDataRepository patientDataRepository) {
        this.patientDataRepository = patientDataRepository;
    }

    public List<PatientDataDTO> findAll() {
        return patientDataRepository.findAll().stream()
                .map(PatientDataDTO::fromEntity)
                .toList();
    }

    public Optional<PatientDataDTO> update(long id, PatientDataDTO patientDataDTO) {
        Optional<PatientEntity> patientEntity = patientDataRepository.findById(id);
        return patientEntity.map(entity -> PatientDataDTO
                .fromEntity(PatientDataDTO
                        .updateMap(patientDataRepository
                                .save(entity), patientDataDTO)));
    }
}