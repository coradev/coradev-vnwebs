package com.coradev.vnwebs.repository;

import com.coradev.vnwebs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
