package com.timelogs.server.controllers;

import com.timelogs.server.entities.User;
import com.timelogs.server.entities.UserDTO;

import com.timelogs.server.services.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/timelogs-api/v1/users")
@CrossOrigin(origins = "http://localhost:8081")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Iterable<User> getUsers() {
        return this.userService.getUsers();
    }

    @PostMapping
    public User addUser(@RequestBody UserDTO user) {
        return this.userService.addUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable(name = "id") Long id, @RequestBody UserDTO user) {
        return this.userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable(name = "id") Long id) {
        return this.userService.deleteUser(id);
    }

}
