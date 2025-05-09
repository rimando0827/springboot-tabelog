package com.example.springboot_tabelog.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.springboot_tabelog.entity.Shop;
import com.example.springboot_tabelog.from.ShopEditForm;
import com.example.springboot_tabelog.from.ShopRegisterForm;
import com.example.springboot_tabelog.repository.ShopRepository;

@Service
public class ShopService {
	private final ShopRepository shopRepository;    
	   
	   public ShopService(ShopRepository shopRepository) {
	       this.shopRepository = shopRepository;        
	   }    
	   
	   @Transactional
	   public void create(ShopRegisterForm shopRegisterForm) {
		   Shop shop = new Shop();        
	       MultipartFile imageFile = shopRegisterForm.getImageFile();
	       
	       if (!imageFile.isEmpty()) {
	           String imageName = imageFile.getOriginalFilename(); 
	           String hashedImageName = generateNewFileName(imageName);
	           Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
	           copyImageFile(imageFile, filePath);
	           shop.setImageName(hashedImageName);
	       }
	       
	       shop.setCategory(shopRegisterForm.getCategory());   
	       shop.setName(shopRegisterForm.getName());                
	       shop.setDescription(shopRegisterForm.getDescription());
	       shop.setPriceLowerLimit(shopRegisterForm.getPriceLowerLimit());
	       shop.setPriceUpperLimit(shopRegisterForm.getPriceUpperLimit());
	       shop.setOpeningTimes(shopRegisterForm.getOpeningTimes());
	       shop.setClosedTimes(shopRegisterForm.getClosedTimes());
	       shop.setPostalCode(shopRegisterForm.getPostalCode());
	       shop.setAddress(shopRegisterForm.getAddress());
	       shop.setPhoneNumber(shopRegisterForm.getPhoneNumber());
	       shop.setClosedDay(shopRegisterForm.getClosedDay());
	       
	       shopRepository.save(shop);
	   }  
	   
	   @Transactional
	    public void update(ShopEditForm shopEditForm) {
		   Shop shop = shopRepository.getReferenceById(shopEditForm.getId());
	        MultipartFile imageFile = shopEditForm.getImageFile();
	        
	        if (!imageFile.isEmpty()) {
	            String imageName = imageFile.getOriginalFilename(); 
	            String hashedImageName = generateNewFileName(imageName);
	            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
	            copyImageFile(imageFile, filePath);
	            shop.setImageName(hashedImageName);
	        }
	        
	           shop.setCategory(shopEditForm.getCategory());   
		       shop.setName(shopEditForm.getName());                
		       shop.setDescription(shopEditForm.getDescription());
		       shop.setPriceLowerLimit(shopEditForm.getPriceLowerLimit());
		       shop.setPriceUpperLimit(shopEditForm.getPriceUpperLimit());
		       shop.setOpeningTimes(shopEditForm.getOpeningTimes());
		       shop.setClosedTimes(shopEditForm.getClosedTimes());
		       shop.setPostalCode(shopEditForm.getPostalCode());
		       shop.setAddress(shopEditForm.getAddress());
		       shop.setPhoneNumber(shopEditForm.getPhoneNumber());
		       shop.setClosedDay(shopEditForm.getClosedDay());
	                    
		       shopRepository.save(shop);
	    }    
	   
	   // UUIDを使って生成したファイル名を返す
	   public String generateNewFileName(String fileName) {
	       String[] fileNames = fileName.split("\\.");                
	       for (int i = 0; i < fileNames.length - 1; i++) {
	           fileNames[i] = UUID.randomUUID().toString();            
	       }
	       String hashedFileName = String.join(".", fileNames);
	       return hashedFileName;
	   }     
	   
	   // 画像ファイルを指定したファイルにコピーする
	   public void copyImageFile(MultipartFile imageFile, Path filePath) {           
	       try {
	           Files.copy(imageFile.getInputStream(), filePath);
	       } catch (IOException e) {
	           e.printStackTrace();
	       }          
	   } 

}
