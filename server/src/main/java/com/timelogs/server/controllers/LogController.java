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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/timelogs-api/v1/logs")
public class LogController {

    private final UserRepository userRepository;
    private final LogRepository logRepository;

    public LogController(UserRepository userRepository, LogRepository logRepository) {
        this.userRepository = userRepository;
        this.logRepository = logRepository;
    }

    private Log checkLoginStatus(Date date, Long userId) {
        return this.logRepository.findByDateAndUserId(date, userId);
    }

    private User checkValidUser(Long userId) {
        Optional<User> userOptional = this.userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid User");
        }

        return userOptional.get();
    }

    @GetMapping("/{userId}")
    public Iterable<Log> getUserLogs(@PathVariable(name = "userId") Long userId) {
        return this.logRepository.findByUserId(userId);
    }

    @PostMapping("/{userId}/checkin")
    public Log checkUserIn(@RequestBody LogDTO log, @PathVariable(name = "userId") Long userId) {

        Log newLog = new Log();

        User user = checkValidUser(userId);

        newLog.setDate(Date.valueOf(log.getDate().substring(0, 10)));

        if (checkLoginStatus(newLog.getDate(), userId) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already checked in today");

        }
        newLog.setTimeIn(Time.valueOf(log.getDate().substring(11, 16) + ":00"));
        newLog.setUserId(userId);

        user.getLogs().add(newLog);

        this.userRepository.save(user);

        Optional<Log> latestLog = this.logRepository.findById(user.getLogs().get(user.getLogs().size() - 1).getLogId());
        if (!latestLog.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't save log");
        }
        return latestLog.get();

    }

    @PutMapping("/{userId}/checkout")
    public Log checkUserOut(@RequestBody LogDTO log, @PathVariable(name = "userId") Long userId) {

        checkValidUser(userId);

        Log checkIn = this.logRepository.findByDateAndUserId(Date.valueOf(log.getDate().substring(0, 10)), userId);

        if (checkIn == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please check in first");
        }

        Log checkCheckOut = this.logRepository.findByDateAndTimeOutAndUserId(
                Date.valueOf(log.getDate().substring(0, 10)),
                Time.valueOf(log.getDate().substring(11, 16) + ":00"),
                userId);

        if (checkCheckOut != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already checked out today");
        }

        checkIn.setTimeOut(Time.valueOf(log.getDate().substring(11, 16) + ":00"));

        return this.logRepository.save(checkIn);

    }

}
