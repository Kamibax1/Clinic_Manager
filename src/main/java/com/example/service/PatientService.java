package com.example.service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.dto.patient.request.UpdatePatientFullInformationRequest;
import com.example.model.dto.patient.response.PatientShortInfoResponse;
import com.example.model.dto.patient.response.UpdatePatientFullInformationResponse;
import com.example.model.entity.PatientEntity;
import com.example.model.entity.UserEntity;
import com.example.repository.PatientRepository;
import com.example.repository.UserRepository;
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
    private final UserRepository userRepository;

    public PatientService(PatientRepository patientRepository, UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UpdatePatientFullInformationResponse updateFullInfo(long id, UpdatePatientFullInformationRequest request) {
        log.info("Обновление полной информации пациента с ID: {}", id);
        return patientRepository.findById(id)
                .map(patient -> {
                    patient.setFirstName(request.getFirstName());
                    patient.setLastName(request.getLastName());
                    String middleName = request.getMiddleName();
                    patient.setMiddleName(middleName != null && !middleName.isEmpty() ? middleName : "-");
                    patient.setDateOfBirth(request.getDateOfBirth());
                    patient.setGender(request.getGender());
                    patient.setPhoneNumber(request.getPhoneNumber());

                    patientRepository.save(patient);
                    log.info("Полная информация пациента с ID: {} успешно обновлена", id);
                    return UpdatePatientFullInformationResponse.fromEntity(patient);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", id));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteById(long id) {
        PatientEntity patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", id));
        Long userId = patient.getUser().getId();
        if (!existsById(userId)) {
            throw new ResourceNotFoundException("User", id);
        }
        userRepository.deleteById(userId);
        log.info("Пользователь с ID: {} успешно удален", id);
    }

    public boolean existsById(long id) {
        return patientRepository.existsById(id);
    }
}
