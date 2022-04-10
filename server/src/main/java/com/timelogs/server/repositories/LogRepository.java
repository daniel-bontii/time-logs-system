package com.timelogs.server.repositories;

import java.sql.Date;
import java.sql.Time;

import com.timelogs.server.entities.Log;

import org.springframework.data.repository.CrudRepository;

public interface LogRepository extends CrudRepository<Log, Long> {
    public Log findByDateAndUserId(Date date, Long userId);

    public Log findByDateAndTimeOutAndUserId(Date date, Time time, Long userId);

    public Iterable<Log> findByUserId(Long userId);
}
