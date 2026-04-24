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

    List<AppointmentEntity> findAllByPatientId(@Param("patientId") Long patientId);

    List<AppointmentEntity> findAllByDoctorId(@Param("id") Long id);

    int countByPatientId(@Param("patientId") Long patientId);

    int countByDoctorId(@Param("id") Long id);

    int countByStatusStatus(@Param("status") StatusEnum status);

    int countByDate(@Param("date") LocalDate date);

    int countByPatientIdAndStatusStatusIn(
            @Param("patientId") Long patientId,
            @Param("statuses") List<StatusEnum> statuses
    );

    int countByDoctorIdAndStatusStatusIn(
            @Param("doctorId") Long doctorId,
            @Param("statuses") List<StatusEnum> statuses
    );

    @Query("SELECT a FROM AppointmentEntity a WHERE " +
            "LOWER(a.doctor.firstName) LIKE LOWER(CONCAT('%', :doctorName, '%')) OR " +
            "LOWER(a.doctor.lastName) LIKE LOWER(CONCAT('%', :doctorName, '%')) OR " +
            "LOWER(a.doctor.middleName) LIKE LOWER(CONCAT('%', :doctorName, '%'))")
    List<AppointmentEntity> findAllShortInfoByDoctorName(@Param("doctorName") String doctorName);
}
