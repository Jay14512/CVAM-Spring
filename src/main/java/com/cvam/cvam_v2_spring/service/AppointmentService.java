package com.cvam.cvam_v2_spring.service;

import com.cvam.cvam_v2_spring.exception.AppointmentConflictException;
import com.cvam.cvam_v2_spring.exception.AppointmentNotFoundException;
import com.cvam.cvam_v2_spring.exception.InvalidAppointmentException;
import com.cvam.cvam_v2_spring.model.Appointment;
import com.cvam.cvam_v2_spring.repository.AppointmentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;




@Service //This makes Spring manage this class as a reusable Bean
public class AppointmentService {
    //Database repository dependency
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional
    public void bookAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new InvalidAppointmentException("Appointment data cannot be null.");
        }

        //Rule 1: Reject duplicate IDs (Database lookup)

            if (appointmentRepository.existsByAppointmentIdIgnoreCase(appointment.getAppointmentId())) {
                throw new AppointmentConflictException("Booking failed: Appointment ID already exists.");
            }


        //Rule 2: Case-insensitive double-booking guard via DB cross-referencing
String doctorLicense = appointment.getDoctor().getMedicalLicenseNumber();
            if (appointmentRepository.existsByDoctorMedicalLicenseNumberIgnoreCaseAndDateTime(doctorLicense, appointment.getDateTime())) {
                throw new AppointmentConflictException("Appointment already exists for this doctor at this time.");
            }

            //Save directly to MySQL
        appointmentRepository.save(appointment);
    }

    @Transactional
    public void cancelAppointment(String appointmentId) {
        if (appointmentId == null || appointmentId.trim().isEmpty()) {
            throw new InvalidAppointmentException("Invalid input: Appointment ID cannot be empty.");
        }
//Look up the appointment, or instantly throw custom Not Found Exception
        Appointment appointment = appointmentRepository.findByAppointmentIdIgnoreCase(appointmentId)
                .orElseThrow(()-> new AppointmentNotFoundException("Appointment not found."));

        appointmentRepository.delete(appointment);
    }


    public List<Appointment> getAppointments() {
        //Fetches every appointment row out of the database table
        return appointmentRepository.findAll();
    }

    public List<Appointment> getAppointmentsForCitizen(String fiscalCode, boolean ascending) {
        if (fiscalCode == null || fiscalCode.trim().isEmpty()) {
            throw new InvalidAppointmentException("Fiscal Code cannot be empty.");
        }

        //Dynamically building database sort strategy based on the 'ascending' argument
        Sort sort = buildDynamicSort(ascending);

        return appointmentRepository.findByUserFiscalCodeIgnoreCase(fiscalCode, sort);
    }


    public List<Appointment> getAppointmentsForDoctor(String doctorId, boolean ascending) {
        if (doctorId == null || doctorId.trim().isEmpty()) {
            throw new InvalidAppointmentException("Doctor ID cannot be empty.");
        }
        Sort sort = buildDynamicSort(ascending);
        return appointmentRepository.findByDoctorMedicalLicenseNumberIgnoreCase(doctorId, sort);
        }

    /**
     * Replaces custom in-memory sort methods by using Spring's native data Sort configurations.
     */
    private Sort buildDynamicSort(boolean ascending){
        Sort.Direction direction = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;
        return Sort.by(direction, "dateTime").and(Sort.by(direction, "appointmentId"));
}


}
