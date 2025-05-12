package com.example.springboot_tabelog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot_tabelog.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

}
