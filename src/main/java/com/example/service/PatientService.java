package com.example.service;

import com.example.model.dto.PatientDTO;
import com.example.model.entity.PatientEntity;
import com.example.repository.PatientRepository;
import org.springframework.stereotype.Service;

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

    public Optional<PatientDTO> findById(long id) {
        Optional<PatientEntity> patientEntity = patientRepository.findById(id);
        return patientEntity.map(PatientDTO::fromEntity);
    }

    public Optional<List<PatientDTO>> findByPartFullName(String partFullName){
        Optional<List<PatientEntity>> patients = patientRepository.searchByFullName(partFullName);
        return patients.map(list -> list.stream()
                .map(PatientDTO::fromEntity)
                .toList());
    }

    public Optional<PatientDTO> update(long id, PatientDTO patientDTO) {
        Optional<PatientEntity> patientEntity = patientRepository.findById(id);
        return patientEntity.map(entity -> {
            PatientEntity updated = PatientDTO.updateMap(entity, patientDTO);
            return PatientDTO.fromEntity(updated);
        });
    }
}
