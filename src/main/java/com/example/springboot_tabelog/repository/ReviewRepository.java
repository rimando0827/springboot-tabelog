package com.example.springboot_tabelog.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot_tabelog.entity.Review;
import com.example.springboot_tabelog.entity.User;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	
	public Page<Review> findByIdOrderByCreatedAtDesc(Integer id, Pageable pageable );
	
	public Page<Review> findByShopIdOrderByCreatedAtDesc(String ShopIdKeyword, Pageable pageable);
	
	public Page<Review> findByShopIdOrderByCreatedAtDesc(Integer shopId, Pageable pageable );
	
	public Page<Review> findByShopOrderByCreatedAtDesc(User review, Pageable pageable );
	
	List<Review> findByShopIdOrderByCreatedAtDesc(Integer houseId);
	
	 @Query("SELECT r FROM Review r WHERE r.shop.name LIKE %:keyword% ORDER BY r.createdAt DESC")
	    Page<Review> findByShopNameContainingOrderByCreatedAtDesc(@Param("keyword") String keyword, Pageable pageable);

	public Page<Review> findAllByOrderByCreatedAtDesc(Pageable pageable);
	
    @Modifying
    @Transactional
    @Query("DELETE FROM Review r WHERE r.shop.id = :shopId")
    void deleteByShopId(Integer shopId);


}
