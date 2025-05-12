package com.example.springboot_tabelog.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot_tabelog.entity.Reservation;
import com.example.springboot_tabelog.entity.Shop;
import com.example.springboot_tabelog.entity.User;
import com.example.springboot_tabelog.from.ReservationCancelForm;
import com.example.springboot_tabelog.from.ReservationRegisterForm;
import com.example.springboot_tabelog.repository.ReservationRepository;
import com.example.springboot_tabelog.repository.ShopRepository;
import com.example.springboot_tabelog.repository.UserRepository;
@Service
public class ReservationService {
	
	private final ReservationRepository reservationRepository;
	private final ShopRepository shopRepository;
	private final UserRepository userRepository;
	
	public ReservationService(ReservationRepository reservationRepository, ShopRepository shopRepository,
			UserRepository userRepository) {
		this.reservationRepository = reservationRepository;
		this.shopRepository = shopRepository;
		this.userRepository = userRepository;
	}
	
	@Transactional
	public void create(ReservationRegisterForm reservationRegisterForm) {
		Reservation reservation = new Reservation();
		Shop shop = shopRepository.getReferenceById(reservationRegisterForm.getShopId());
		User user = userRepository.getReferenceById(reservationRegisterForm.getUserId());
		DateTimeFormatter dtFt = DateTimeFormatter.ofPattern("yyyy年MM月dd日 H時mm分");
		LocalDateTime reservationDate = LocalDateTime.parse(reservationRegisterForm.getReservationDateTime(), dtFt);

		reservation.setShop(shop);
		reservation.setUser(user);
		reservation.setReservationDateTime(reservationDate);
		reservation.setNumberOfPeople(reservationRegisterForm.getNumberOfPeople());

		reservationRepository.save(reservation);
	}
	
	@Transactional
	public void cancel(ReservationCancelForm reservationCancelForm) {
		reservationRepository.deleteById(reservationCancelForm.getId());
	}
	
	// 予約日時が現在時刻以降かをチェックする
		public boolean isReservationDateWhenCurrentTimeAfter(String reservationDate, String reservationTime) {
			LocalDateTime reservation = LocalDateTime.parse(reservationDate + "T" + reservationTime);
			LocalDateTime now = LocalDateTime.now();
			return reservation.isAfter(now);
		}


}
