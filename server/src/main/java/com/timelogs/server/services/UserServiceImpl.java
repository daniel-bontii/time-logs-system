package com.timelogs.server.services;

import com.timelogs.server.entities.User;
import com.timelogs.server.entities.UserDTO;
import com.timelogs.server.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Iterable<User> getUsers() {
        return this.userRepository.getUserDetails();
    }

    @Override
    public User addUser(UserDTO user) {

        User check = this.userRepository.findByEmail(user.getEmail());

        if (check != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already in use");
        }

        if (user.getEmail() == null || user.getDepartment() == null || user.getName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please fill all fields");
        }

        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setDepartment(user.getDepartment());
        newUser.setRole("user");
        newUser.setPassword((new BCryptPasswordEncoder().encode("1111")));

        return this.userRepository.save(newUser);
    }

    @Override
    public User updateUser(Long id, UserDTO user) {

        Optional<User> userToUpdateOptional = this.userRepository.findById(id);

        if (!userToUpdateOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user");
        }

        User userToUpdate = userToUpdateOptional.get();

        if (user.getName() != null) {
            userToUpdate.setName(user.getName());
        }

        if (user.getDepartment() != null) {
            userToUpdate.setDepartment(user.getDepartment());
        }

        if (user.getEmail() != null) {
            userToUpdate.setEmail(user.getEmail());
        }

        return this.userRepository.save(userToUpdate);

    }

    @Override
    public User deleteUser(Long id) {

        Optional<User> userToDeleteOptional = this.userRepository.findById(id);

        if (userToDeleteOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user");
        }

        User userToDelete = userToDeleteOptional.get();
        this.userRepository.delete(userToDelete);
        return userToDelete;
    }
}
