package com.timelogs.server.controllers;

import com.timelogs.server.entities.UserDTO;

import com.timelogs.server.services.LoginService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/timelogs-api/v1/login")
@CrossOrigin(origins = "http://localhost:8081")
public class LoginController {

    private final LoginService loginService;

    public LoginController(final LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public UserDTO validateLogin(@RequestBody UserDTO userDTO) {
        return this.loginService.validateLogin(userDTO);
    }

    @GetMapping("/{userId}")
    public UserDTO getLoggedInUser(@PathVariable(name = "userId") Long userId) {
        return this.loginService.getLoggedInUser(userId);
    }

}
