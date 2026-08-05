package com.cvam.cvam_v2_spring.repository;

import com.cvam.cvam_v2_spring.model.Appointment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    //1. Used to reject duplicate string IDs (Case-insensitive)
    boolean existsByAppointmentIdIgnoreCase(String appointmentId);

    //2. Used to find a single appointment for deletion
    Optional<Appointment> findByAppointmentIdIgnoreCase(String appointmentId);

    //3. Used for Rule 2: Case-insensitive double-booking guard
    boolean existsByDoctorMedicalLicenseNumberIgnoreCaseAndDateTime(String medicalLicenseNumber, LocalDateTime dateTime);

    //4. Find and sort appointments for a Citizen using their nested User's Fiscal code
    //Spring reads the path: citizen -> user -> fiscalCode
    List<Appointment> findByCitizenUserFiscalCodeIgnoreCase(String fiscalCode, Sort sort);

    //5. Find and sort appointments for a Doctor using their Medical License Number
    List<Appointment> findByDoctorMedicalLicenseNumberIgnoreCase(String medicalLicenseNumber, Sort sort);
}
