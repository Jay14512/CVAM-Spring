package com.cvam.cvam_v2_spring.service;

import com.cvam.cvam_v2_spring.exception.EmailAlreadyRegisteredException;
import com.cvam.cvam_v2_spring.exception.FiscalCodeAlreadyRegisteredException;
import com.cvam.cvam_v2_spring.model.User;
import com.cvam.cvam_v2_spring.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;


    //Injecting both repositories into a single service layer (Constructor Injection)
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    //Fetch all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //2. Find a specific user by ID
    public Optional<User> getUserById(Long id) {
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


}