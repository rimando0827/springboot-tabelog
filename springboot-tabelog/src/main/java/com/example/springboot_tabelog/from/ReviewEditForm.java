package com.example.springboot_tabelog.from;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewEditForm {
	
	@NotNull
	private Integer id;

	private int shopId;

	private int userId;

	private int score;

	@NotNull(message = "レビュー内容を再入力してください")
	private String content;


}
