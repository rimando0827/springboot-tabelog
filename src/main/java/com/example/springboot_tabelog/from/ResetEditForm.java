package com.example.springboot_tabelog.from;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResetEditForm {
	
	@NotNull
	private String token;
	
	 @NotBlank(message = "パスワードを入力してください。")
	    @Length(min = 8, message = "パスワードは8文字以上で入力してください。")
	    private String password;    
	    
	    @NotBlank(message = "パスワード（確認用）を入力してください。")
	    private String passwordConfirmation;  

}
