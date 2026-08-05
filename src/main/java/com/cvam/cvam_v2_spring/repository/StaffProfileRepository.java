package com.cvam.cvam_v2_spring.repository;

import com.cvam.cvam_v2_spring.model.StaffProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface StaffProfileRepository extends JpaRepository<StaffProfile, Long>{

    //1. Check if a specific staff code already exists
    boolean existsByStaffCode(String staffCode);

    //2. Find a specific assignment by its staff code
    Optional<StaffProfile> findByStaffCode(String staffCode);

    //3. Find all staff job instances assigned to a specific physical User ID
    List<StaffProfile> findByUserId(Long userId);

    //4. Find all staff members working under a specific Doctor's profile ID
    List<StaffProfile> findByDoctorId(Long doctorId);
}