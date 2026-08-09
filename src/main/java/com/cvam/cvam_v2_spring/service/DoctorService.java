package com.cvam.cvam_v2_spring.service;

import com.cvam.cvam_v2_spring.model.DoctorProfile;
import com.cvam.cvam_v2_spring.model.User;
import com.cvam.cvam_v2_spring.repository.DoctorProfileRepository;
import com.cvam.cvam_v2_spring.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final UserRepository userRepository;
    private final DoctorProfileRepository doctorProfileRepository;


    //Inject all 3 dependencies through the constructor
    public DoctorService(UserRepository userRepository, DoctorProfileRepository doctorProfileRepository) {
        this.userRepository = userRepository;
        this.doctorProfileRepository = doctorProfileRepository;

    }

    /**
     * Handles administrative onboarding for new medical doctors.
     */

    @Transactional
    public DoctorProfile onboardDoctor(User userDetails, String medicalLicenseNumber, String officePhone) {
        //1. Validate global User account details uniqueness
        if (userRepository.existsByEmail(userDetails.getEmail())) {
            throw new IllegalArgumentException("Email is already registered.");
        }
        if (userRepository.existsByFiscalCode(userDetails.getFiscalCode())) {
            throw new IllegalArgumentException(("Fiscal Code is already registered."));
        }

        //2. Validate Doctor-specific business rules uniqueness
        if (doctorProfileRepository.existsByMedicalLicenseNumber(medicalLicenseNumber)) {
            throw new IllegalArgumentException("Medical license number is already registered.");
        }

        //3. Persist core physical user identity details
        User savedUser = userRepository.save(userDetails);

        //4. Construct and attach the administrative professional profile
        DoctorProfile doctorProfile = new DoctorProfile(savedUser, medicalLicenseNumber, officePhone);

        //5. Persist and return profile
        return doctorProfileRepository.save(doctorProfile);
    }


    public List<DoctorProfile> getAllDoctors() {
        return doctorProfileRepository.findAll();
    }

    public Optional<DoctorProfile> getDoctorById(Long id) {
        return doctorProfileRepository.findById(id);
    }

}
