package com.cvam.cvam_v2_spring.repository;

import com.cvam.cvam_v2_spring.model.DoctorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, Long> {
    //Check if a license is already taken before saving a new doctor
    boolean existsByMedicalLicenseNumber(String medicalLicenseNumber);

    //Find a doctor by their specific license number
    Optional<DoctorProfile> findByMedicalLicenseNumber(String medicalLicenseNumber);
}