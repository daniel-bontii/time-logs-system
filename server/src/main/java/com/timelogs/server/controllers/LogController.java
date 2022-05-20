package com.timelogs.server.controllers;

import com.timelogs.server.entities.Log;
import com.timelogs.server.entities.LogDTO;

import com.timelogs.server.services.LogService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/timelogs-api/v1/logs")
@CrossOrigin(origins = "http://localhost:8081")
public class LogController {

    private final LogService logService;

    public LogController(final LogService logService) {
        this.logService = logService;
    }

    @GetMapping
    public Iterable<Log> getUserNameAndLogs() {
        return this.logService.getUserNameAndLogs();
    }

    @GetMapping("/all/{date}")
    public Iterable<Log> getLogsByDate(@PathVariable(name = "date") String date) {
        return this.logService.getLogsByDate(date);
    }

    @GetMapping("/{userId}")
    public Iterable<Log> getUserLogs(@PathVariable(name = "userId") Long userId) {
        return this.logService.getUserLogs(userId);
    }

    @PostMapping("/{userId}/checkin")
    public Log checkUserIn(@RequestBody LogDTO log, @PathVariable(name = "userId") Long userId) {
       return this.logService.checkUserIn(log, userId);
    }

    @PutMapping("/{userId}/checkout")
    public Log checkUserOut(@RequestBody LogDTO log, @PathVariable(name = "userId") Long userId) {
       return this.logService.checkUserOut(log, userId);
    }

}
