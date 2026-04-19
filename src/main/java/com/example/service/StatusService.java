package com.example.service;

import com.example.model.dto.StatusDTO;
import com.example.model.enums.StatusEnum;
import com.example.repository.StatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {
    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Optional<StatusDTO> findById(Long id) {
        return statusRepository.findById(id).map(StatusDTO::fromEntity);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<StatusDTO> findAll() {
        return statusRepository.findAll().stream()
                .map(StatusDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public StatusDTO findByStatus(StatusEnum status) {
        return StatusDTO.fromEntity(statusRepository.findByStatus(status));
    }
}
