package com.cvam.cvam_v2_spring.controller;

import com.cvam.cvam_v2_spring.dto.AppointmentRequest;
import com.cvam.cvam_v2_spring.model.Appointment;
import com.cvam.cvam_v2_spring.model.CitizenProfile;
import com.cvam.cvam_v2_spring.model.DoctorProfile;
import com.cvam.cvam_v2_spring.model.User;
import com.cvam.cvam_v2_spring.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    //Spring automatically injects the AppointmentService bean here.
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        //1. Create a User for the Citizen
        User citizenUser = new User(
                "Luigi",
                "Verdi",
                "VRDLGU75A01H501Z",
                "luigi@verdi.com",
                "+3928974157",
                LocalDate.of(1987, 9, 28)
        );

        DoctorProfile doctor = new DoctorProfile(
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

    @PostMapping //This listens for HTTP POST requests to /api/appointments
    public String bookNewAppointment(@RequestBody AppointmentRequest request) {
        //@RequestBody tells Spring to parse incoming JSON directly into an Appointment object
        //1. The DTO caught the flat data from the web.
        //2. We recunstruct the deep, valid domain objects using the DTO data,
        CitizenProfile citizen = new CitizenProfile(
                "Luigi", "Verdi", request.getFiscalCode(),
                "luigi@verdi.com", "+3928974157", java.time.LocalDate.of(1987, 9, 28)
        );

        DoctorProfile doctor = new DoctorProfile(
                "Francesca", "Neri", "NRIFNC80A01H501Z",
                "francesca@dottore.it", request.getDoctorId()
        );

        //3. This triggers the self-protecting constructor guards!
        Appointment cleanAppointment = new Appointment(
                request.getAppointmentId(),
                citizen,
                doctor,
                request.getDateTime(),
                request.getVaccineType()
        );

        //4. Pass the pristine, fully validated model to the service engine
        appointmentService.bookAppointment(cleanAppointment);
        return "Appointment booked successfully with ID: " + cleanAppointment.getAppointmentId();
    }

}
