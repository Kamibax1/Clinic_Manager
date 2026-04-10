package com.example.repository;

import com.example.model.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {
    @Query("SELECT d FROM DoctorEntity d WHERE " +
            "LOWER(d.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
            "LOWER(d.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<DoctorEntity> searchByName(@Param("name") String name);
}
