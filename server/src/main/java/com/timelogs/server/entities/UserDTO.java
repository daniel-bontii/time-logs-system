package com.timelogs.server.entities;

import lombok.Getter;

@Getter
public class UserDTO {

    private String name;
    private String email;
    private String department;
    private String password;
    private String role;

}
