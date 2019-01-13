package com.sbc.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbc.authentication.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    Role findByRole(String role);
    Role findOne(int id);
}
