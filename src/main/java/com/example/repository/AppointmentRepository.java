package com.example.repository;

import com.example.model.entity.AppointmentEntity;
import com.example.model.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity,Long> {
    @Query("SELECT s FROM AppointmentEntity s WHERE s.doctor.getId() = :id")
    Optional<List<AppointmentEntity>> findAllAppointmentByDoctorId(@Param("id") Long id);

    @Query("SELECT s FROM AppointmentEntity s WHERE s.status.getStatus() = :status")
    Optional<List<AppointmentEntity>> findAllAppointmentByStatus(@Param("status") StatusEnum status);
}
