package com.example.springboot_tabelog.from;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class ReservationInputForm {
	   @NotBlank(message = "予約日を設定してください")
	    private String checkinDate;    
	    
	    @NotNull(message = "来店人数を設定してください")
	    @Min(value = 1, message = "来店人数は1人以上に設定してください。")
	    private Integer numberOfPeople; 

	    @NotBlank(message = "時間を設定してください")
	    private String timeDate;   

	    
	    }

