package com.cvam.cvam_v2_spring.service;

import com.cvam.cvam_v2_spring.exception.AppointmentConflictException;
import com.cvam.cvam_v2_spring.exception.AppointmentNotFoundException;
import com.cvam.cvam_v2_spring.exception.InvalidAppointmentException;
import com.cvam.cvam_v2_spring.model.Appointment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Service //This makes Spring manage this class as a reusable Bean
public class AppointmentService {
    //For now, we keep the in-memory list, which will later be replaced by MySQL
    private final List<Appointment> appointments = new ArrayList<>();

    public void bookAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new InvalidAppointmentException("Appointment data cannot be null.");
        }

        //Rule 1: Reject duplicate IDs
        for (Appointment existing : appointments) {
            if (existing.getAppointmentId().equalsIgnoreCase(appointment.getAppointmentId())) {
                throw new AppointmentConflictException("Booking failed: Appointment ID already exists.");
            }
        }

        //Rule 2: Case-insensitive double-booking guard
        for (Appointment existing : appointments) {
            if (existing.getDoctor().getDoctorId().equalsIgnoreCase(appointment.getDoctor().getDoctorId())
                    && existing.getDateTime().equals(appointment.getDateTime())) {
                throw new AppointmentConflictException("Appointment already exists for this doctor at this time.");
            }
        }
        appointments.add(appointment);
    }

    public void cancelAppointment(String appointmentId) {
        if (appointmentId == null || appointmentId.trim().isEmpty()) {
            throw new InvalidAppointmentException("Invalid input: Appointment ID cannot be empty.");
        }

        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getAppointmentId().equalsIgnoreCase(appointmentId)) {
                appointments.remove(i);
                return;
            }
        }
        throw new AppointmentNotFoundException("Appointment not found.");
    }

    private void sortAppointments(List<Appointment> list, boolean ascending) {
        Comparator<Appointment> ascendingComparator = Comparator.comparing(Appointment::getDateTime)
                .thenComparing(Appointment::getAppointmentId);

        if (ascending) {
            list.sort(ascendingComparator);
        } else {
            list.sort(Comparator.comparing(Appointment::getDateTime).reversed()
                    .thenComparing(Comparator.comparing(Appointment::getAppointmentId).reversed()));
        }
    }

    public List<Appointment> getAppointments() {
        return Collections.unmodifiableList(appointments);
    }

    public List<Appointment> getAppointmentsForCitizen(String fiscalCode, boolean ascending) {
        if (fiscalCode == null || fiscalCode.trim().isEmpty()) {
            throw new InvalidAppointmentException("Fiscal Code cannot be empty.");
        }

        List<Appointment> filtered = new ArrayList<>();
        for (Appointment appt : appointments) {
            if (appt.getCitizen().getFiscalCode().equalsIgnoreCase(fiscalCode)) {
                filtered.add(appt);
            }
        }
        sortAppointments(filtered, ascending);
        return Collections.unmodifiableList(filtered);
    }

    public List<Appointment> getAppointmentsForDoctor(String doctorId, boolean ascending) {
        if (doctorId == null || doctorId.trim().isEmpty()) {
            throw new InvalidAppointmentException("Doctor ID cannot be empty.");
        }
        List<Appointment> filtered = new ArrayList<>();
        for (Appointment appt : appointments) {
            if (appt.getDoctor().getDoctorId().equalsIgnoreCase(doctorId)) {
                filtered.add(appt);
            }
        }

        sortAppointments(filtered, ascending);
        return Collections.unmodifiableList(filtered);
    }


}
