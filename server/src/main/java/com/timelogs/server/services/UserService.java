package com.timelogs.server.services;

import com.timelogs.server.entities.User;
import com.timelogs.server.entities.UserDTO;

public interface UserService {
    Iterable<User> getUsers();
    User addUser(UserDTO user);
    User updateUser(Long id, UserDTO user);
    User deleteUser(Long id);
}
