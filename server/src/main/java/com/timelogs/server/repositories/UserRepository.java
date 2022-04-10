package com.timelogs.server.repositories;

import com.timelogs.server.entities.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public User findByEmail(String email);

    @Query("SELECT new map(u.id as userId, u.name as name, u.email as email, u.department as department) FROM User u WHERE u.role ='user'")
    public Iterable<User> getUserDetails();

}
