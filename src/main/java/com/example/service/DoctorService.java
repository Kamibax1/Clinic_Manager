package com.example.service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.dto.SpecializationDTO;
import com.example.model.dto.doctor.request.UpdateDoctorFullInformationRequest;
import com.example.model.dto.doctor.response.DoctorFullInformationResponse;
import com.example.model.dto.doctor.response.DoctorShortInfoResponse;
import com.example.model.entity.DoctorEntity;
import com.example.repository.DoctorRepository;
import com.example.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    public DoctorService(DoctorRepository doctorRepository, UserRepository userRepository) {
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<DoctorShortInfoResponse> findAllShortInfo() {
        return doctorRepository.findAll().stream()
                .map(DoctorShortInfoResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<DoctorShortInfoResponse> findAllShortInfoByName(String name) {
        return doctorRepository.findAllByName(name).stream()
                .map(DoctorShortInfoResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<DoctorShortInfoResponse> findAllShortInfoBySpecialization(String specialization) {
        return doctorRepository.findAllBySpecializationsName(specialization).stream()
                .map(DoctorShortInfoResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public DoctorFullInformationResponse findFullInfoById(long id) {
        log.info("Поиск полной информации о враче по ID: {}", id);
        return doctorRepository.findById(id)
                .map(DoctorFullInformationResponse::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", id));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public DoctorFullInformationResponse updateFullInfo(long id, UpdateDoctorFullInformationRequest request) {
        log.info("Обновление полной информации врача с ID: {}", id);
        return doctorRepository.findById(id)
                .map(doctor -> {
                    doctor.setFirstName(request.getFirstName());
                    doctor.setLastName(request.getLastName());
                    String middleName = request.getMiddleName();
                    doctor.setMiddleName(middleName != null && !middleName.isEmpty() ? middleName : "-");
                    doctor.setPhoneNumber(request.getPhoneNumber());

                    doctorRepository.save(doctor);
                    log.info("Полная информация пациента с ID: {} успешно обновлена", id);
                    return DoctorFullInformationResponse.fromEntity(doctor);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", id));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteById(long id) {
        DoctorEntity doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", id));
        Long userId = doctor.getUser().getId();
        if (!doctorRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", id);
        }
        userRepository.deleteById(userId);
        log.info("Пользователь с ID: {} успешно удален", id);
    }

    public boolean existsById(long id) {
        return doctorRepository.existsById(id);
    }
}
