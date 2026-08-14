package com.cvam.cvam_v2_spring.util;

import com.cvam.cvam_v2_spring.dto.DoctorImportDto;
import com.cvam.cvam_v2_spring.dto.StaffImportDto;
import com.cvam.cvam_v2_spring.dto.UserImportDto;
import com.cvam.cvam_v2_spring.model.DoctorProfile;
import com.cvam.cvam_v2_spring.model.StaffProfile;
import com.cvam.cvam_v2_spring.model.User;
import com.cvam.cvam_v2_spring.repository.AppointmentRepository;
import com.cvam.cvam_v2_spring.repository.DoctorProfileRepository;
import com.cvam.cvam_v2_spring.repository.StaffProfileRepository;
import com.cvam.cvam_v2_spring.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;


@Component
public class DataSeeder implements CommandLineRunner {
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final StaffProfileRepository staffProfileRepository;
    private final AppointmentRepository appointmentRepository;


    public DataSeeder(
            ObjectMapper objectMapper,
            UserRepository userRepository,
            DoctorProfileRepository doctorProfileRepository,
            StaffProfileRepository staffProfileRepository,
            AppointmentRepository appointmentRepository
    ) {
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
        this.doctorProfileRepository = doctorProfileRepository;
        this.staffProfileRepository = staffProfileRepository;
        this.appointmentRepository = appointmentRepository;

    }

    @Override
    public void run(String... args) {
        seedUsers();
        seedDoctors();
        seedStaff();
        //seedAppointments();
    }

    private void seedUsers() {
        //1. read file from resources
        ClassPathResource resource = new ClassPathResource("mock_users.json");
        try (InputStream inputStream = resource.getInputStream()) {

            //2. deserialize JSON into UserImportDto objects using Jackson
            //TypeReference constructs a generic list binding safely
            List<UserImportDto> dtos = objectMapper.readValue(inputStream, new TypeReference<List<UserImportDto>>() {
            });

            //3. loop through each dto
            for (UserImportDto dto : dtos) {
                //4. check for dublicates
                //if already exists -> skip
                if (userRepository.existsByFiscalCode(dto.getFiscalCode())) {
                    System.out.println("User with Fiscal Code " + dto.getFiscalCode() + "already exists. Skipping");
                    continue;
                }
                //5. build real User entity from dto fields
                User user = new User(
                        dto.getFirstName(),
                        dto.getLastName(),
                        dto.getFiscalCode(),
                        dto.getEmail(),
                        dto.getPhoneNumber(),
                        dto.getBirthDate()
                );


                //6. save with userRepository
                userRepository.save(user);
            }
            System.out.println("User seeding completed successfully!");


        } catch (Exception e) {
            //Catches general file system errors or unchecked parsin exceptions
            throw new RuntimeException("Failed to seed users data from JSON file", e);
        }
    }

    //Same logic for Citizens, Doctors, Staff, Appointments
    private void seedDoctors() {
        //1. read file from resources
        ClassPathResource resource = new ClassPathResource("mock_doctors.json");

        try (InputStream inputStream = resource.getInputStream()) {
            //2. Deserialize JSON into a list of DoctorImportDto objects using Jackson
            //TypeReference constructs a generic list binding safely
            List<DoctorImportDto> dtos = objectMapper.readValue(inputStream, new TypeReference<List<DoctorImportDto>>() {

            });

            //3. Fetch the corresponding User account from the database
            // Doctor profiles depend on an existing user record
            for (DoctorImportDto dto : dtos) {
                User user = userRepository.findUserByFiscalCode((dto.getUserFiscalCode()))
                        .orElseThrow(() -> new RuntimeException("Cannot seed doctor. User not found for Fiscal Code: " + dto.getUserFiscalCode()));

                if (doctorProfileRepository.existsByMedicalLicenseNumber((dto.getMedicalLicenseNumber()))) {
                    System.out.println("Doctor with License Number " + dto.getMedicalLicenseNumber() + "already exists. Skipping");
                    continue;
                }

                //4. Map the fields from DTO to the real Entity
                DoctorProfile doctor = new DoctorProfile(
                        user,
                        dto.getMedicalLicenseNumber(),
                        dto.getOfficePhoneNumber()
                );

                //6. Save final entity
                doctorProfileRepository.save(doctor);
            }


            System.out.println("Doctor seeding completed successfully!");

        } catch (Exception e) {
            throw new RuntimeException("Failed to seed doctors data", e);
        }
    }

    private void seedStaff() {
        //1. read file from resources
        ClassPathResource resource = new ClassPathResource("mock_staff.json");

        try (InputStream inputStream = resource.getInputStream()) {
            //2. Deserialize JSON into a list of StaffImportDto objects using Jackson
            //TypeReference constructs a generic list binding safely
            List<StaffImportDto> dtos = objectMapper.readValue(inputStream, new TypeReference<List<StaffImportDto>>() {
            });

            //3. Fetch the corresponding User account from the database
            // Staff profiles depend on an existing user record
            for (StaffImportDto dto : dtos) {
                User user = userRepository.findUserByFiscalCode((dto.getUserFiscalCode()))
                        .orElseThrow(() -> new RuntimeException("Cannot seed Staff. User not found for Fiscal Code " + dto.getUserFiscalCode()));

                if (staffProfileRepository.existsByStaffCode((dto.getStaffCode()))) {
                    System.out.println("Staff with Staff Code " + dto.getStaffCode() + "already exists. Skipping");
                    continue;
                }
                //4. Map the fields from DTO to the real Entity
                StaffProfile staff = new StaffProfile(
                        dto.getStaffCode(),
                        user
                );
                //6. Save final entity
                staffProfileRepository.save(staff);

            }
            System.out.println("Staff seeding completed successfully!");

        } catch (Exception e) {
            throw new RuntimeException("Failed to seed staff data", e);
        }
    }


}


