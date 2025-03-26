package com.example.springboot_tabelog.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot_tabelog.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
