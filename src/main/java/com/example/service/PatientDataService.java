package com.example.service;

import com.example.model.dto.profile.PatientDataDTO;
import com.example.model.entity.PatientEntity;
import com.example.repository.PatientDataRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientDataService {

    private PatientDataRepository patientDataRepository;

    public PatientDataService(PatientDataRepository patientDataRepository) {
        this.patientDataRepository = patientDataRepository;
    }

    public List<PatientDataDTO> findAll() {
        return patientDataRepository.findAll().stream()
                .map(PatientDataDTO::fromEntity)
                .toList();
    }

    public Optional<PatientDataDTO> update(long id, PatientDataDTO patientDataDTO) {
        return patientDataRepository.findById(id)
                .map(existing -> {
                    existing.setFullName(patientDataDTO.getFullName());
                    existing.setDateOfBirth(patientDataDTO.getDateOfBirth());
                    existing.setGender(patientDataDTO.getGender());
                    existing.setPhoneNumber(patientDataDTO.getPhoneNumber());

                    PatientEntity updated = patientDataRepository.save(existing);
                    return PatientDataDTO.fromEntity(updated);
                });
    }
}
