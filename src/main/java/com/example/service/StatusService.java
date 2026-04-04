package com.example.service;

import com.example.model.entity.StatusEntity;
import com.example.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {
    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public Optional<StatusEntity> findById(Long id) {
        return statusRepository.findById(id);
    }

    public List<StatusEntity> findAll() {
        return statusRepository.findAll();
    }
}
