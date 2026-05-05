package com.example.repository;

import com.example.model.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {
    List<DoctorEntity> findAllBySpecializationsName(String specialization);

    Optional<DoctorEntity> findByUser_Id(Long userId);

    @Query("SELECT d FROM DoctorEntity d WHERE " +
            "LOWER(d.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
            "LOWER(d.lastName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
            "LOWER(d.middleName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<DoctorEntity> findAllByName(@Param("name") String name);

}
