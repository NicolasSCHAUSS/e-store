package com.store.estore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.estore.model.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege,Long>{
  
}
