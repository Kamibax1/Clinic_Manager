package com.example.service;

import com.example.model.dto.DoctorShortInfoResponse;
import com.example.model.dto.SpecializationDTO;
import com.example.model.entity.SpecializationEntity;
import com.example.repository.DoctorRepository;
import com.example.repository.SpecializationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final SpecializationRepository specializationRepository;

    public DoctorService(DoctorRepository doctorRepository, SpecializationRepository specializationRepository) {
        this.doctorRepository = doctorRepository;
        this.specializationRepository = specializationRepository;
    }

    @Transactional(readOnly = true)
    public List<DoctorShortInfoResponse> findAllShortInfo() {
        return doctorRepository.findAll().stream()
                .map(DoctorShortInfoResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<DoctorShortInfoResponse> findAllShortInfoByName(String name) {
        return doctorRepository.findAllShortInfoByName(name).stream()
                .map(DoctorShortInfoResponse::fromEntity)
                .toList();
    }
}
