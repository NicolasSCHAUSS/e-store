package com.store.estore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.estore.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
  Optional<User> findByEmail(String email);
  Boolean existsByEmail(String email);
}
