package com.example.springboot_tabelog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot_tabelog.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
	public Page<Shop> findByNameLike(String keyword, Pageable pageable);
	
	public Page<Shop> findByNameLikeOrAddressLikeOrderByCreatedAtDesc(String nameKeyword, String addressKeyword, Pageable pageable);  
	public Page<Shop> findByNameLikeOrAddressLikeOrderByPriceLowerLimitAsc(String nameKeyword, String addressKeyword, Pageable pageable);
	
	public Page<Shop> findByCategoryIdOrderByCreatedAtDesc(Integer categoryId, Pageable pageable);
	public Page<Shop> findByCategoryIdOrderByPriceLowerLimitAsc(Integer categoryId, Pageable pageable);
	                  
	
	public Page<Shop> findAllByOrderByCreatedAtDesc(Pageable pageable);
	public Page<Shop> findAllByOrderByPriceLowerLimitAsc(Pageable pageable);    
	
	public List<Shop> findTop10ByOrderByCreatedAtDesc();
	
	public List<Shop> findByCategoryId(Integer categoryId);
	Optional<Shop> findById(Integer id);

}
