package com.example.springboot_tabelog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot_tabelog.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	public Role findByName(String name);    

}
