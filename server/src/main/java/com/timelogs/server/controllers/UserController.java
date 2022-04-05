package com.timelogs.server.controllers;

import com.timelogs.server.entities.User;
import com.timelogs.server.entities.UserDTO;
import com.timelogs.server.repositories.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/timelogs-api/v1/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }

    @PostMapping
    public User addUser(@RequestBody UserDTO user) {

        User check = this.userRepository.findByEmail(user.getEmail());

        if (check != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already in use");
        }

        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setDepartment(user.getDepartment());
        newUser.setPassword("1111");
        newUser.setRole("user");
        return this.userRepository.save(newUser);
    }

}
