package com.timelogs.server.controllers;

import com.timelogs.server.entities.User;
import com.timelogs.server.entities.UserDTO;
import com.timelogs.server.repositories.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/timelogs-api/v1/login")
@CrossOrigin(origins = "http://localhost:8081")
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public UserDTO validadeLogin(@RequestBody UserDTO userDTO) {

        User user = this.userRepository.findByEmail(userDTO.getEmail());

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid User");
        }

        if (!(new BCryptPasswordEncoder().matches(userDTO.getPassword(), user.getPassword()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong password");
        }

        User foundUser = this.userRepository.findByEmail(userDTO.getEmail());

        UserDTO loggedInUser = new UserDTO();
        loggedInUser.setName(foundUser.getName());
        loggedInUser.setRole(foundUser.getRole());
        loggedInUser.setUserId(foundUser.getUserId());

        return loggedInUser;

    }

}
