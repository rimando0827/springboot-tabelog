package com.example.springboot_tabelog.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot_tabelog.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
	public Page<Favorite> findByUserIdOrderByCreatedAtDesc(Integer useId, Pageable pageable );
	Optional<Favorite> findByShopIdAndUserId(Integer shopId, Integer userId);
	
	@Modifying
    @Transactional
    @Query("DELETE FROM Favorite f WHERE f.shop.id = :shopId")
    void deleteByShopId(Integer shopId);
}
