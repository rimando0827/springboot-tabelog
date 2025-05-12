package com.example.springboot_tabelog.from;

import java.time.LocalTime;

import org.springframework.web.multipart.MultipartFile;

import com.example.springboot_tabelog.entity.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ShopEditForm {
	  @NotNull
	  private Integer id;
	
	  private Category category; 
	  
	  @NotBlank(message = "店舗名を入力してください。")
	    private String name;
	        
	    private MultipartFile imageFile;
	    
	    @NotBlank(message = "説明を入力してください。")
	    private String description;   
	    
	    @NotBlank(message = "最低料金を入力してください")
	    private String priceLowerLimit;  
	    
	    @NotBlank(message = "最低料金を入力してください")
	    private String priceUpperLimit;
	    
	    @NotNull(message = "営業開始時間を入力してください")
	    private LocalTime openingTimes ;     
	    
	    @NotNull(message = "営業終了時間を入力してください")
	    private LocalTime closedTimes ;
	    
	    @NotBlank(message = "郵便番号を入力してください。")
	    private String postalCode;
	    
	    @NotBlank(message = "住所を入力してください。")
	    private String address;
	    
	    @NotBlank(message = "電話番号を入力してください。")
	    private String phoneNumber;
	    
	    @NotBlank(message = "定休曜日を入力してください。")
	    private String closedDay;
	
	
}
