package com.example.springboot_tabelog.from;

import org.springframework.web.multipart.MultipartFile;

import com.example.springboot_tabelog.entity.Category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class ShopRegisterForm {
	  @NotBlank(message = "店舗名を入力してください。")
	    private String name;
	  
	    private Category category; 
	        
	    private MultipartFile imageFile;
	    
	    @NotBlank(message = "説明を入力してください。")
	    private String description;   
	    
	    @NotBlank(message = "最低料金を入力してください")
	    private String priceLowerLimit;  
	    
	    @NotBlank(message = "最低料金を入力してください")
	    private String priceUpperLimit;
	    
	    @NotBlank(message = "営業開始時間を入力してください")
	    private String openingTimes ;     
	    
	    @NotBlank(message = "営業終了時間を入力してください")
	    private String closedTimes ;
	    
	    @NotBlank(message = "郵便番号を入力してください。")
	    private String postalCode;
	    
	    @NotBlank(message = "住所を入力してください。")
	    private String address;
	    
	    @NotBlank(message = "電話番号を入力してください。")
	    private String phoneNumber;
	    
	    @NotBlank(message = "定休曜日を入力してください。")
	    private String closedDay;
	
	
}
