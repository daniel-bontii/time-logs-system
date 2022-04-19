package com.timelogs.server.controllers;

import java.util.Optional;

import com.timelogs.server.entities.User;
import com.timelogs.server.entities.UserDTO;
import com.timelogs.server.repositories.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;

@RestController
@RequestMapping("/timelogs-api/v1/users")
@CrossOrigin(origins = "http://localhost:8081")
public class UserController {

    private final UserRepository userRepository;

    public UserController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Iterable<User> getUsers() {
        return this.userRepository.getUserDetails();

    }

    @PostMapping
    public User addUser(@RequestBody UserDTO user) {

        User check = this.userRepository.findByEmail(user.getEmail());

        if (check != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already in use");
        }

        if (user.getEmail() == null || user.getDepartment() == null || user.getName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please fill all fields");
        }

        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setDepartment(user.getDepartment());
        newUser.setRole("user");
        newUser.setPassword((new BCryptPasswordEncoder().encode("1111")));

        return this.userRepository.save(newUser);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable(name = "id") Long id, @RequestBody UserDTO user) {

        Optional<User> userToUpdateOptional = this.userRepository.findById(id);

        if (!userToUpdateOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user");
        }

        User userToUpdate = userToUpdateOptional.get();

        if (user.getName() != null) {
            userToUpdate.setName(user.getName());
        }

        if (user.getDepartment() != null) {
            userToUpdate.setDepartment(user.getDepartment());
        }

        if (user.getEmail() != null) {
            userToUpdate.setEmail(user.getEmail());
        }

        return this.userRepository.save(userToUpdate);

    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable(name = "id") Long id) {

        Optional<User> userToDeleteOptional = this.userRepository.findById(id);

        if (!userToDeleteOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user");
        }

        User userToDelete = userToDeleteOptional.get();
        this.userRepository.delete(userToDelete);
        return userToDelete;
    }

}
