package com.example.springboot_tabelog.from;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationRegisterForm {
	
	private Integer userId;

	private Integer shopId;

	private String reservationDateTime;

	private Integer count;

}
