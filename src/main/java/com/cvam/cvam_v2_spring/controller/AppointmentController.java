package com.cvam.cvam_v2_spring.controller;

import com.cvam.cvam_v2_spring.model.Appointment;
import com.cvam.cvam_v2_spring.model.Citizen;
import com.cvam.cvam_v2_spring.model.Doctor;
import com.cvam.cvam_v2_spring.service.AppointmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class AppointmentController {

    private final AppointmentService appointmentService;

    //Spring automatically injects the AppointmentService bean here.
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/api/appointments")
    public List<Appointment> getAllAppointments() {
        Citizen citizen = new Citizen(
                "Luigi",
                "Verdi",
                "VRDLGU75A01H501Z",
                "luigi@verdi.com",
                "+3928974157",
                LocalDate.of(1987, 9, 28)
        );

        Doctor doctor = new Doctor(
                "Francesca",
                "Neri",
                "NRIFNC80A01H501Z",
                "francesca@dottore.it",
                "556988"
        );

        //Pre-populating a test record for demo purposes
        if (appointmentService.getAppointments().isEmpty()) {
            appointmentService.bookAppointment(new Appointment(
                    "APPT-1001",
                    citizen,
                    doctor,
                    LocalDateTime.of(2026, 8, 3, 10, 30),
                    "Pfizer"
            ));
        }

        return appointmentService.getAppointments();
    }
}
