package org.example.repository;

import org.example.entity.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDtls, Integer> {

    public UserDtls findByEmail(String email);    //checking email provided by user if it is in database or not

}
