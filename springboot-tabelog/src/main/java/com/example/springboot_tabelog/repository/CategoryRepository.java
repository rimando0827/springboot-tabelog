package com.example.springboot_tabelog.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot_tabelog.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	public Page<Category> findBynameLike(String name, Pageable pageable );

}
