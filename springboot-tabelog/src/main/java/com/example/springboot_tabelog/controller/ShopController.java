package com.example.springboot_tabelog.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springboot_tabelog.entity.Review;
import com.example.springboot_tabelog.entity.Shop;
import com.example.springboot_tabelog.repository.CategoryRepository;
import com.example.springboot_tabelog.repository.ReviewRepository;
import com.example.springboot_tabelog.repository.ShopRepository;

@Controller
@RequestMapping("/shops")
public class ShopController {
	
	private final ShopRepository shopRepository;     
	private final CategoryRepository categoryRepository;
	private final ReviewRepository reviewRepository;
	
	public ShopController(ShopRepository shopRepository , CategoryRepository categoryRepository, ReviewRepository reviewRepository) {
        this.shopRepository = shopRepository;     
        this.categoryRepository = categoryRepository;
        this.reviewRepository= reviewRepository;
        
}
	
	  @GetMapping
	  public String index(@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			  @RequestParam(name = "keyword", required = false) String keyword,
			  @RequestParam(name = "category", required = false) Integer categoryId,
			  @RequestParam(name = "order", required = false) String order,
              Model model) {
		  
		  
		  Page<Shop> shopPage;

			 
			if(categoryId != null) {
				if(order != null && order.equals("priceLowerLimitAsc")) {
					shopPage = shopRepository.findByCategoryIdOrderByPriceLowerLimitAsc(categoryId, pageable);
				} else {
					shopPage = shopRepository.findByCategoryIdOrderByCreatedAtDesc(categoryId, pageable);
				}
				
		        
			}
			
			else if (keyword != null && !keyword.isEmpty()) {
				if(order != null && order.equals("priceLowerLimitAsc")) {
					shopPage = shopRepository.findByNameLikeOrAddressLikeOrderByPriceLowerLimitAsc("%" + keyword + "%", "%" + keyword + "%", pageable);	
				} else {
					shopPage = shopRepository.findByNameLikeOrAddressLikeOrderByCreatedAtDesc("%" + keyword + "%", "%" + keyword + "%", pageable);
				}
			}
			
			else {
				if (order != null && order.equals("priceLowerLimitAsc")) {
					shopPage = shopRepository.findAllByOrderByPriceLowerLimitAsc(pageable);
				} else {
					
					shopPage = shopRepository.findAllByOrderByCreatedAtDesc(pageable);
				}
				
			}
			

			model.addAttribute("shopPage", shopPage);
			model.addAttribute("keyword", keyword);
			model.addAttribute("categoryId", categoryId);
			model.addAttribute("categoryList" , categoryRepository.findAll());
			model.addAttribute("order", order);
		  
		  return "shops/index";
	  }
	  
	  @GetMapping("/{id}")
	    public String show(@PathVariable(name = "id") Integer id, Model model) {
	        Shop shop = shopRepository.getReferenceById(id);
	        
	        List<Review> newReview= reviewRepository.findByShopIdOrderByCreatedAtDesc(id);
	        
	        List<Review> displayedReviews = newReview.size() > 6 ? newReview.subList(0, 6) : newReview;
	        model.addAttribute("newReview",newReview);
	        model.addAttribute("displayedReviews", displayedReviews);
	        model.addAttribute("totalReviewCount", newReview.size());
	        model.addAttribute("shop", shop);         
	        
	        return "shops/show";
	    }    
}