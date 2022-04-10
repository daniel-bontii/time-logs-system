package com.timelogs.server.repositories;

import java.sql.Date;
import java.sql.Time;

import com.timelogs.server.entities.Log;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LogRepository extends CrudRepository<Log, Long> {
    public Log findByDateAndUserId(Date date, Long userId);

    public Log findByDateAndTimeOutAndUserId(Date date, Time time, Long userId);

    public Iterable<Log> findByUserId(Long userId);

    @Query("SELECT new map(l.logId as logId, l.timeIn as timeIn, l.timeOut as timeOut, l.indicator as indicator, l.date as date, u.name as name) FROM Log l join User u ON l.logId = u.userId")
    public Iterable<Object[]> findByUserNameAndLogs();
}
