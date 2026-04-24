package com.example.service;

import com.example.model.dto.patient.request.UpdatePatientFullInformationRequest;
import com.example.model.dto.patient.response.PatientShortInfoResponse;
import com.example.model.dto.patient.response.UpdatePatientFullInformationResponse;
import com.example.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<PatientShortInfoResponse> findAllShortInfo() {
        return patientRepository.findAll().stream()
                .map(PatientShortInfoResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<PatientShortInfoResponse> findAllShortInfoByName(String name) {
        return patientRepository.findAllShortInfoByName(name).stream()
                .map(PatientShortInfoResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Optional<UpdatePatientFullInformationResponse> updateFullInfo(long id, UpdatePatientFullInformationRequest request) {
        log.info("Обновление полной информации пациента с ID: {}", id);
        return patientRepository.findById(id).map(patient -> {
            patient.setFirstName(request.getFirstName());
            patient.setLastName(request.getLastName());
            String middleName = request.getMiddleName();
            patient.setMiddleName(middleName != null && !middleName.isEmpty() ? middleName : "-");
            patient.setDateOfBirth(request.getDateOfBirth());
            patient.setGender(request.getGender());
            patient.setPhoneNumber(request.getPhoneNumber());

            patientRepository.save(patient);
            log.info("ID Пациента: {} успешно обновлен", id);
            return UpdatePatientFullInformationResponse.fromEntity(patient);
        });
    }
}
