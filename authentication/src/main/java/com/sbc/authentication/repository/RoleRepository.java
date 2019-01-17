package com.sbc.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbc.authentication.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByRole(String role);
}
