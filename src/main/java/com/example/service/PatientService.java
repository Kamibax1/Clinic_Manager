package com.example.service;

import com.example.model.dto.PatientDTO;
import com.example.model.entity.PatientEntity;
import com.example.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientDTO> findAll() {
        return patientRepository.findAll().stream()
                .map(PatientDTO::fromEntity)
                .toList();
    }

    @Transactional
    public Optional<PatientDTO> findById(long id) {
        Optional<PatientEntity> patientEntity = patientRepository.findById(id);
        return patientEntity.map(PatientDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public List<PatientDTO> findByPartFullName(String partFullName){
        List<PatientEntity> patients = patientRepository.findByFullNameContainingIgnoreCase(partFullName);
        return patients.stream()
                .map(PatientDTO::fromEntity)
                .toList();
    }

    @Transactional
    public Optional<PatientDTO> update(long id, PatientDTO patientDTO) {
        Optional<PatientEntity> patientEntity = patientRepository.findById(id);
        return patientEntity.map(entity -> {
            PatientEntity updated = PatientDTO.updateMap(entity, patientDTO);
            return PatientDTO.fromEntity(updated);
        });
    }
}
