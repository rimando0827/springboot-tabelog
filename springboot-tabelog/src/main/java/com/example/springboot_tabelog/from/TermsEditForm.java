package com.example.springboot_tabelog.from;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TermsEditForm {
	 @NotNull
	    private Integer id;  
	 
	   @NotBlank(message = "規約を入力してください。")
	    private String description;   

}
