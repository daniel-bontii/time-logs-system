package com.timelogs.server.repositories;

import java.sql.Date;

import com.timelogs.server.entities.Log;

import org.springframework.data.repository.CrudRepository;

public interface LogRepository extends CrudRepository<Log, Long> {
    public Log findByDateAndUserId(Date date, Long userId);
}
