package com.example.repository;

import com.example.model.dto.DoctorsDTO;
import com.example.model.entity.DoctorSpecializationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainRepository extends JpaRepository<DoctorSpecializationEntity,Integer> {
}
