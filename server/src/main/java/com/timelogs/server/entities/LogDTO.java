package com.timelogs.server.entities;

import lombok.Getter;

@Getter
public class LogDTO {

    private Long id;
    private String date;
    private String timeIn;
    private String timeOut;
    private String indicator;
}
