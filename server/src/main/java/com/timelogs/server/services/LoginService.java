package com.timelogs.server.services;

import com.timelogs.server.entities.UserDTO;

public interface LoginService {
    UserDTO validadeLogin(UserDTO userDTO);
    UserDTO getLoggedInUser(Long userId);
}
