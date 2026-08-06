package com.cvam.cvam_v2_spring.service;

import com.cvam.cvam_v2_spring.exception.EmailAlreadyRegisteredException;
import com.cvam.cvam_v2_spring.exception.FiscalCodeAlreadyRegisteredException;
import com.cvam.cvam_v2_spring.model.CitizenProfile;
import com.cvam.cvam_v2_spring.model.User;
import com.cvam.cvam_v2_spring.repository.CitizenProfileRepository;
import com.cvam.cvam_v2_spring.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    private final UserRepository userRepository;
    private final CitizenProfileRepository citizenProfileRepository;

    //Injecting both repositories into a single service layer (Constructor Injection)
    public UserService(UserRepository userRepository, CitizenProfileRepository citizenProfileRepository){
        this.userRepository = userRepository;
        this.citizenProfileRepository = citizenProfileRepository;
    }
    //Fetch all users
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //2. Find a specific user by ID
    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    //3. Create a new user with uniqueness checks
    @Transactional //Ensures database safety
    public User registerUser(User user) {
        //Check uniqueness before saving to prevent raw SQL constraint exception
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyRegisteredException();
        }

        if (userRepository.existsByFiscalCode(user.getFiscalCode())) {
            throw new FiscalCodeAlreadyRegisteredException();
        }
        //Save to Database (equivalent to $user->save() in Eloquent)
        return userRepository.save(user);
    }
        /**
         *
         * Registers a new physical person and instantly assigns them a Citizen Profile.
         * @Transactional ensures that if either save fails, the entire operation rolls back.
         */

        @Transactional
        public CitizenProfile registerCitizen (User newUser){
            //1. Perform a database-level uniqueness validation
            if (userRepository.existsByEmail(newUser.getEmail())) {
                throw new EmailAlreadyRegisteredException();
            }
            if (userRepository.existsByFiscalCode(newUser.getFiscalCode())) {
                throw new FiscalCodeAlreadyRegisteredException();
            }

            //2. Persist the User record to generate the database ID
            User savedUser = userRepository.save(newUser);

            //3. Create the Citizen Profile linked to ouw newly persisted user
            CitizenProfile citizenProfile = new CitizenProfile(savedUser);

            //4. Save the profile and return it
            return citizenProfileRepository.save(citizenProfile);
        }




}