package com.realestate.realestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realestate.realestate.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

}
