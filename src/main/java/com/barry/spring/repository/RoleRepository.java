package com.barry.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.barry.spring.model.Role;
import com.barry.spring.model.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	//@Query("SELECT R FROM Role R WHERE R.name=:name")
	Optional<Role> findByName(RoleName roleName);
}