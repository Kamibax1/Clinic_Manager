package com.example.service;

import com.example.model.dto.profile.PatientDTO;
import com.example.model.entity.PatientEntity;
import com.example.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientDataService {

    private final PatientRepository patientRepository;

    public PatientDataService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientDTO> findAll() {
        return patientRepository.findAll().stream()
                .map(PatientDTO::fromEntity)
                .toList();
    }

    public Optional<PatientDTO> update(long id, PatientDTO patientDTO) {
        Optional<PatientEntity> patientEntity = patientRepository.findById(id);
        return patientEntity.map(entity -> PatientDTO
                .fromEntity(PatientDTO
                        .updateMap(patientRepository
                                .save(entity), patientDTO)));
    }
}
