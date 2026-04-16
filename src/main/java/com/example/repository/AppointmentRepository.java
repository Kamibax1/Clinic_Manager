package com.example.repository;

import com.example.model.entity.AppointmentEntity;
import com.example.model.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity,Long> {

    List<AppointmentEntity> findAllByStatusStatus(@Param("status") StatusEnum status);

    List<AppointmentEntity> findAllByDate(@Param("date") LocalDate date);

    @Query("SELECT a FROM AppointmentEntity a WHERE " +
            "LOWER(a.doctor.firstName) LIKE LOWER(CONCAT('%', :doctorName, '%')) OR " +
            "LOWER(a.doctor.lastName) LIKE LOWER(CONCAT('%', :doctorName, '%')) OR " +
            "LOWER(a.doctor.middleName) LIKE LOWER(CONCAT('%', :doctorName, '%'))")
    List<AppointmentEntity> findAllShortInfoByDoctorName(@Param("doctorName") String doctorName);
}
