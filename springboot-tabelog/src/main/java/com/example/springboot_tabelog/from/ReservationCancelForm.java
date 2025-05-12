package com.example.springboot_tabelog.from;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationCancelForm {
	@NotNull
    private Integer id;
}
