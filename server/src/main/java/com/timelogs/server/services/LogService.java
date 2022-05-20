package com.timelogs.server.services;

import com.timelogs.server.entities.Log;
import com.timelogs.server.entities.LogDTO;

public interface LogService {
    Iterable<Log> getUserNameAndLogs();
    Iterable<Log> getLogsByDate(String date);
    Iterable<Log> getUserLogs(Long userId);
    Log checkUserIn(LogDTO log, Long userId);
    Log checkUserOut(LogDTO log,  Long userId);
}
