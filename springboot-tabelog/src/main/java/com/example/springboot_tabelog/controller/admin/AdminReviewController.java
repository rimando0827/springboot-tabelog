package com.example.springboot_tabelog.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springboot_tabelog.entity.Review;
import com.example.springboot_tabelog.repository.ReviewRepository;
import com.example.springboot_tabelog.repository.ShopRepository;

@Controller
@RequestMapping("/admin/reviews")
public class AdminReviewController {
	
	private ReviewRepository reviewRepository;
	private ShopRepository shopRepository;

	public AdminReviewController(
			
			ReviewRepository reviewRepository,ShopRepository shopRepository) {
		
		this.reviewRepository = reviewRepository;
		this.shopRepository =  shopRepository;
	}
	
	
	@GetMapping
	public String index(
			@PageableDefault(page = 0, size = 10) Pageable pageable, Model model,
			@RequestParam(name = "keyword", required = false) String keyword) {
		
		
		
		Page<Review> reviewPage; 

		 if (keyword != null && !keyword.isEmpty()) {
		        // キーワードが提供された場合、Shop名に基づいて検索
		        reviewPage = reviewRepository.findByShopNameContainingOrderByCreatedAtDesc(keyword, pageable);
		    } else {
		        // キーワードがない場合は、全てのレビューを取得
		        reviewPage = reviewRepository.findAllByOrderByCreatedAtDesc(pageable);
		    }
		
		
		model.addAttribute("reviewPage",reviewPage);
		model.addAttribute("shopList",shopRepository.findAll());
		model.addAttribute("keyword", keyword);

		return "admin/reviews/index";
	}
	
	
}
