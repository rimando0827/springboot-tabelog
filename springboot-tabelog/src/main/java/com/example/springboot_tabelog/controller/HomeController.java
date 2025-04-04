package com.example.springboot_tabelog.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.springboot_tabelog.entity.Shop;
import com.example.springboot_tabelog.repository.CategoryRepository;
import com.example.springboot_tabelog.repository.ShopRepository;



@Controller
public class HomeController {
	private final ShopRepository shopRepository; 
	private final CategoryRepository categoryRepository;
	
	public HomeController(ShopRepository shopRepository,CategoryRepository categoryRepository) {
		this.shopRepository =shopRepository;
		this.categoryRepository = categoryRepository;
	}
	
	@GetMapping("/")
	public String index(Model model ) {
		List<Shop> newShops = shopRepository.findTop10ByOrderByCreatedAtDesc();
		
		
		model.addAttribute("newShops",newShops);
		model.addAttribute("categoryList" , categoryRepository.findAll());
		return "index";
	}
	
	 @GetMapping("/{id}")
	    public String show(@PathVariable(name = "id") Integer id, Model model) {
	        Shop shop = shopRepository.getReferenceById(id);
	        
	        model.addAttribute("shop", shop);         
	        
	        return "houses/show";
	    }    

}
