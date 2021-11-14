package org.ksun.pets.udemy.course.instademo.service;

import org.ksun.pets.udemy.course.instademo.entity.User;
import org.ksun.pets.udemy.course.instademo.entity.enums.ERole;
import org.ksun.pets.udemy.course.instademo.exceptions.UserExistException;
import org.ksun.pets.udemy.course.instademo.payload.request.SignupRequest;
import org.ksun.pets.udemy.course.instademo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(SignupRequest userIn) {
        LOGGER.info("createUser 1 {}", userIn.getEmail());


        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setName(userIn.getFirstname());
        user.setLastname(userIn.getLastname());
        user.setUsername(userIn.getUsername());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);

        try {
            LOGGER.info("Save user {}", userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception e) {
            LOGGER.error("ksan Error during registration {}", e.getMessage());
            throw new UserExistException("The user " + user.getUsername() + " already exist.");
        }
    }
}
