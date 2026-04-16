package com.example.service;

import com.example.model.dto.PatientShortInfoResponse;
import com.example.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional(readOnly = true)
    public List<PatientShortInfoResponse> findAllShortInfo() {
        return patientRepository.findAll().stream()
                .map(PatientShortInfoResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PatientShortInfoResponse> findAllShortInfoByName(String name) {
        return patientRepository.findAllShortInfoByName(name).stream()
                .map(PatientShortInfoResponse::fromEntity)
                .toList();
    }
}
