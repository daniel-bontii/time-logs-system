package com.timelogs.server.services;

import com.timelogs.server.entities.User;
import com.timelogs.server.entities.UserDTO;
import com.timelogs.server.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;

    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO validateLogin(UserDTO userDTO) {

        User user = this.userRepository.findByEmail(userDTO.getEmail());

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid User");
        }

        if (!(new BCryptPasswordEncoder().matches(userDTO.getPassword(), user.getPassword()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong password");
        }

        User foundUser = this.userRepository.findByEmail(userDTO.getEmail());

        UserDTO loggedInUser = new UserDTO();
        loggedInUser.setUserId(foundUser.getUserId());

        return loggedInUser;

    }

    @Override
    public UserDTO getLoggedInUser( Long userId) {
        Optional<User> userOptional = this.userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not a logged In user");
        }

        User foundUser = userOptional.get();

        UserDTO loggedInUser = new UserDTO();
        loggedInUser.setName(foundUser.getName());
        loggedInUser.setRole(foundUser.getRole());
        loggedInUser.setUserId(foundUser.getUserId());

        return loggedInUser;
    }
}
