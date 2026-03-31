package com.example.repository;

import com.example.model.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    @Query("SELECT s FROM PatientEntity s WHERE s.fullName LIKE %:name%")
    Optional<List<PatientEntity>> searchByFullName(@Param("name") String name);
}
