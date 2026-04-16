package com.example.repository;

import com.example.model.entity.StatusEntity;
import com.example.model.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
    StatusEntity findByStatus(StatusEnum status);
}
