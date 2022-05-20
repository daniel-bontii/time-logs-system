package com.timelogs.server.services;

import com.timelogs.server.entities.Log;
import com.timelogs.server.entities.LogDTO;
import com.timelogs.server.entities.User;
import com.timelogs.server.repositories.LogRepository;
import com.timelogs.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

public class LogServiceImpl {
    private LogRepository logRepository;
    private UserRepository userRepository;

    @Autowired
    public LogServiceImpl(LogRepository logRepository, UserRepository userRepository) {
        this.logRepository = logRepository;
        this.userRepository = userRepository;
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

    private String calculateIndicator(String timeIn) {
        final int ARRIVAL_HOUR = Integer.parseInt(timeIn.substring(0, 2));
        final int ARRIVAL_MINUTE = Integer.parseInt(timeIn.substring(3, 5));

        if ((ARRIVAL_HOUR <= 6) || (ARRIVAL_HOUR <= 7 && ARRIVAL_MINUTE <= 30)) {
            return "early";
        } else if ((ARRIVAL_HOUR == 7 && ARRIVAL_MINUTE >= 30) ||
                (ARRIVAL_HOUR == 8 && ARRIVAL_MINUTE >= 0 && ARRIVAL_MINUTE <= 30)) {
            return "in time";
        } else {
            return "late";
        }
    }

    public Iterable<Log> getUserNameAndLogs() {
        return this.logRepository.findByUserNameAndLogs();
    }

    public Iterable<Log> getLogsByDate(@PathVariable(name = "date") String date) {
        return this.logRepository.findByDate(Date.valueOf(date.substring(0, 10)));
    }

    public Iterable<Log> getUserLogs(@PathVariable(name = "userId") Long userId) {
        return this.logRepository.findByUserId(userId);
    }

    public Log checkUserIn(@RequestBody LogDTO log, @PathVariable(name = "userId") Long userId) {
        final String TIME_AND_DATE = log.getDate();
        final String TIME_IN = TIME_AND_DATE.substring(11, 16);
        Log newLog = new Log();

        User user = checkValidUser(userId);

        newLog.setDate(Date.valueOf(TIME_AND_DATE.substring(0, 10)));

        if (checkLoginStatus(newLog.getDate(), userId) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already checked in today");

        }

        newLog.setUserId(userId);

        if (log.getIndicator() == null) {
            newLog.setTimeIn(Time.valueOf(TIME_IN + ":00"));
            String checkInIndicator = calculateIndicator(TIME_IN);
            newLog.setIndicator(checkInIndicator);
        } else {
            newLog.setIndicator(log.getIndicator());
        }

        user.getLogs().add(newLog);

        this.userRepository.save(user);

        Optional<Log> latestLog = this.logRepository.findById(user.getLogs().get(user.getLogs().size() - 1).getLogId());
        if (!latestLog.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't save log");
        }
        return latestLog.get();

    }

    public Log checkUserOut(@RequestBody LogDTO log, @PathVariable(name = "userId") Long userId) {

        checkValidUser(userId);

        Log checkIn = this.logRepository.findByDateAndUserId(Date.valueOf(log.getDate().substring(0, 10)), userId);

        if (checkIn == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please check in first");
        }

        Log checkCheckOut = this.logRepository.findByDateAndUserIdAndTimeOutNotNull(
                Date.valueOf(log.getDate().substring(0, 10)),
                userId);

        if (checkCheckOut != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already checked out today");
        }

        checkIn.setTimeOut(Time.valueOf(log.getDate().substring(11, 16) + ":00"));

        return this.logRepository.save(checkIn);

    }
}
