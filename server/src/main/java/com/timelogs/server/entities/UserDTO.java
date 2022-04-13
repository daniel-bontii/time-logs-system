package com.timelogs.server.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Long userId;
    private String name;
    private String email;
    private String department;
    private String password;
    private String role;

}
