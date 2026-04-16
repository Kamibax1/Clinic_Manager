package com.example.service;

import com.example.model.dto.StatusDTO;
import com.example.model.entity.StatusEntity;
import com.example.repository.StatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {
    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Transactional
    public Optional<StatusDTO> findById(Long id) {
        return statusRepository.findById(id).map(StatusDTO::fromEntity);
    }

    public List<StatusDTO> findAll() {
        return statusRepository.findAll().stream()
                .map(StatusDTO::fromEntity)
                .toList();
    }
}
