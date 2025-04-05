package com.example.springboot_tabelog.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot_tabelog.entity.Review;
import com.example.springboot_tabelog.entity.User;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	
	public Page<Review> findByShopIdOrderByCreatedAtDesc(Integer shopId, Pageable pageable );
	public Page<Review> findByShopOrderByCreatedAtDesc(User review, Pageable pageable );
	
	List<Review> findByShopIdOrderByCreatedAtDesc(Integer houseId);

}
