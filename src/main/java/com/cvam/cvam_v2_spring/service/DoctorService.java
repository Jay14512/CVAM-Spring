package com.cvam.cvam_v2_spring.service;

import com.cvam.cvam_v2_spring.model.DoctorProfile;
import com.cvam.cvam_v2_spring.model.StaffProfile;
import com.cvam.cvam_v2_spring.model.User;
import com.cvam.cvam_v2_spring.repository.DoctorProfileRepository;
import com.cvam.cvam_v2_spring.repository.StaffProfileRepository;
import com.cvam.cvam_v2_spring.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final UserRepository userRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final StaffProfileRepository staffProfileRepository;

    //Inject all 3 dependencies through the constructor
    public DoctorService(UserRepository userRepository, DoctorProfileRepository doctorProfileRepository, StaffProfileRepository staffProfileRepository) {
        this.userRepository = userRepository;
        this.doctorProfileRepository = doctorProfileRepository;
        this.staffProfileRepository = staffProfileRepository;
    }

    /**
     * Handles administrative onboarding for new medical doctors.
     */

    @Transactional
    public DoctorProfile onboardDoctor(User userDetails, String medicalLicenceNumber, String officePhone) {
        //1. Validate global User account details uniqueness
        if (userRepository.existsByEmail(userDetails.getEmail())) {
            throw new IllegalArgumentException("Email is already registered.");
        }
        if (userRepository.existsByFiscalCode(userDetails.getFiscalCode())) {
            throw new IllegalArgumentException(("Fiscal Code is already registered."));
        }

        //2. Validate Doctor-specific business rules uniqueness
        if (doctorProfileRepository.existsByMedicalLicenseNumber(medicalLicenceNumber)) {
            throw new IllegalArgumentException("Medical license number is already registered.");
        }

        //3. Persist core physical user identity details
        User savedUser = userRepository.save(userDetails);

        //4. Construct and attach the administrative professional profile
        DoctorProfile doctorProfile = new DoctorProfile(savedUser, medicalLicenceNumber, officePhone);

        //5. Persist and return profile
        return doctorProfileRepository.save(doctorProfile);
    }


    /**
     * WORKFLOW: Assigns a staff member to work under a specific doctor.
     *
     */

    @Transactional
    public StaffProfile assignStaffToDoctor(User staffUser, Long doctorProfileId, String staffCode) {
        //Validate staff code uniqueness
        if (staffProfileRepository.existsByStaffCode(staffCode)) {
            throw new IllegalArgumentException("Staff code is already assigned to a user.");
        }

        //Verify the target doctor actually exists in the database
        DoctorProfile targetDoctor = doctorProfileRepository.findById(doctorProfileId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor profile not found with ID: " + doctorProfileId));

        //Save the base user entity for the staff member if it's new, or look it up if existing
        User savedStaffUser = userRepository.save(staffUser);

        //Instantiate and persist the StaffProfile link using validated constructor
        StaffProfile newStaffAssignment = new StaffProfile(staffCode, savedStaffUser, targetDoctor);
        return staffProfileRepository.save(newStaffAssignment);

    }

    //Smart lookup helper methods
    public List<StaffProfile> getStaffAssignedToDoctor(Long doctorId) {
        return staffProfileRepository.findByDoctorId(doctorId);
    }


    public List<DoctorProfile> getAllDoctors() {
        return doctorProfileRepository.findAll();
    }

    public Optional<DoctorProfile> getDoctorById(Long id) {
        return doctorProfileRepository.findById(id);
    }

}
