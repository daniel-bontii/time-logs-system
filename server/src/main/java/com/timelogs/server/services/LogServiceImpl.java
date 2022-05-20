package com.timelogs.server.services;

import com.timelogs.server.entities.Log;
import com.timelogs.server.entities.LogDTO;
import com.timelogs.server.entities.User;
import com.timelogs.server.repositories.LogRepository;
import com.timelogs.server.repositories.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

@Service
public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;
    private final UserRepository userRepository;

    @Autowired
    public LogServiceImpl(final LogRepository logRepository, final UserRepository userRepository) {
        this.logRepository = logRepository;
        this.userRepository = userRepository;
    }

    private Log checkLoginStatus(Date date, Long userId) {
        return this.logRepository.findByDateAndUserId(date, userId);
    }

    private User checkValidUser(Long userId) {
        Optional<User> userOptional = this.userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid User");
        }

        return userOptional.get();
    }

    private String calculateIndicator(String timeIn) {
        final int ARRIVAL_HOUR = Integer.parseInt(timeIn.substring(0, 2));
        final int ARRIVAL_MINUTE = Integer.parseInt(timeIn.substring(3, 5));

        if ((ARRIVAL_HOUR <= 6) || (ARRIVAL_HOUR <= 7 && ARRIVAL_MINUTE <= 30)) {
            return "early";
        } else if ((ARRIVAL_HOUR == 7) ||
                (ARRIVAL_HOUR == 8 && ARRIVAL_MINUTE >= 0 && ARRIVAL_MINUTE <= 30)) {
            return "in time";
        } else {
            return "late";
        }
    }


    @Override
    public Iterable<Log> getUserNameAndLogs() {
        return this.logRepository.findByUserNameAndLogs();
    }

    @Override
    public Iterable<Log> getLogsByDate( String date) {
        return this.logRepository.findByDate(Date.valueOf(date.substring(0, 10)));
    }

    @Override
    public Iterable<Log> getUserLogs(Long userId) {
        return this.logRepository.findByUserId(userId);
    }

    @Override
    public Log checkUserIn(LogDTO log, Long userId) {
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
        if (latestLog.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't save log");
        }
        return latestLog.get();

    }

    @Override
    public Log checkUserOut(LogDTO log, Long userId) {

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
