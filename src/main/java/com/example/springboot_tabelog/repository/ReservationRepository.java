package com.example.springboot_tabelog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot_tabelog.entity.Reservation;
import com.example.springboot_tabelog.entity.User;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	 public Page<Reservation> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
	 
	  @Modifying
	    @Transactional
	    @Query("DELETE FROM Reservation r WHERE r.shop.id = :shopId")
	 void deleteByShopId(Integer shopId);
}
