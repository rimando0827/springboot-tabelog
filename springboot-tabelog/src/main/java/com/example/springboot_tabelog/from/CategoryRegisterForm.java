package com.example.springboot_tabelog.from;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRegisterForm {
	  @NotBlank(message = "カテゴリー名を入力してください。")
	    private String name;
	    	
}
