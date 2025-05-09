package com.example.springboot_tabelog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot_tabelog.entity.Term;

public interface TermRepository extends JpaRepository<Term, Integer> {

}
