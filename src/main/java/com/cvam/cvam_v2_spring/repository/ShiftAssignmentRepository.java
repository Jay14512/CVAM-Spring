package com.cvam.cvam_v2_spring.repository;

import com.cvam.cvam_v2_spring.model.ShiftAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShiftAssignmentRepository extends JpaRepository<ShiftAssignment, Long>{
    //1. Find all shifts assigned to a specific staff member's record ID
    List<ShiftAssignment> findStaffById(Long staffId);

    //2. Find all shifts assigned to a specific doctor's record ID
    List<ShiftAssignment> findDoctorById(Long doctorId);

    //3. Find active assignments for a doctor during a specific time block
    //Useful for checking who is on duty during an appointment slot
    @Query(value = "SELECT s FROM ShiftAssignment s WHERE s.doctor.id = :doctorId " + "AND :targetTime BETWEEN s.shiftStart AND s.shiftEnd")
    List<ShiftAssignment> findActiveStaffForDoctorAtTime(
            @Param("doctorId") Long doctorId,
            @Param("targetTime")LocalDateTime targetTime
            );
}
