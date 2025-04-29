package com.example.springboot_tabelog.from;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordEditForm {

	@NotNull
	private Integer id;
	@NotBlank
	private String password;

}
