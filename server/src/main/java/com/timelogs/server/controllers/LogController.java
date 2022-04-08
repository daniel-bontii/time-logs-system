package com.timelogs.server.controllers;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

import com.timelogs.server.entities.Log;
import com.timelogs.server.entities.LogDTO;
import com.timelogs.server.entities.User;
import com.timelogs.server.repositories.LogRepository;
import com.timelogs.server.repositories.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/timelogs-api/v1/logs")
public class LogController {

    private final UserRepository userRepository;
    private final LogRepository logsRepository;

    public LogController(UserRepository userRepository, LogRepository logsRepository) {
        this.userRepository = userRepository;
        this.logsRepository = logsRepository;
    }

    private Log checkLoginStatus(Date date, Long userId) {
        return this.logsRepository.findByDateAndUserId(date, userId);
    }

    @PostMapping("/{userId}/checkin")
    public User addLog(@RequestBody LogDTO log, @PathVariable(name = "userId") Long userId) {

        Log newLog = new Log();
        Optional<User> userOptional = this.userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid User");
        }

        User user = userOptional.get();

        newLog.setDate(Date.valueOf(log.getDate().substring(0, 10)));

        if (checkLoginStatus(newLog.getDate(), userId) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already checked in today");

        }
        newLog.setTimeIn(Time.valueOf(log.getDate().substring(11, 16) + ":00"));
        newLog.setUserId(userId);

        user.getLogs().add(newLog);

        return this.userRepository.save(user);

    }

}
