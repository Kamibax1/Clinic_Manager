package com.example.service;

import com.example.model.dto.DoctorShortInfoResponse;
import com.example.model.dto.SpecializationDTO;
import com.example.model.entity.SpecializationEntity;
import com.example.repository.DoctorRepository;
import com.example.repository.SpecializationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<DoctorShortInfoResponse> findAllShortInfo() {
        return doctorRepository.findAll().stream()
                .map(DoctorShortInfoResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<DoctorShortInfoResponse> findAllShortInfoByName(String name) {
        return doctorRepository.findAllShortInfoByName(name).stream()
                .map(DoctorShortInfoResponse::fromEntity)
                .toList();
    }
}
