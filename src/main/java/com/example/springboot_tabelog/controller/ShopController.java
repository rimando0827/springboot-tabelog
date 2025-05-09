package com.example.springboot_tabelog.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springboot_tabelog.entity.Favorite;
import com.example.springboot_tabelog.entity.Review;
import com.example.springboot_tabelog.entity.Shop;
import com.example.springboot_tabelog.from.ReservationInputForm;
import com.example.springboot_tabelog.repository.CategoryRepository;
import com.example.springboot_tabelog.repository.FavoriteRepository;
import com.example.springboot_tabelog.repository.ReviewRepository;
import com.example.springboot_tabelog.repository.ShopRepository;
import com.example.springboot_tabelog.security.UserDetailsImpl;

@Controller
@RequestMapping("/shops")
public class ShopController {
	
	private final ShopRepository shopRepository;     
	private final CategoryRepository categoryRepository;
	private final ReviewRepository reviewRepository;
	private final FavoriteRepository favoriteRepository;
	
	public ShopController(ShopRepository shopRepository , CategoryRepository categoryRepository, ReviewRepository reviewRepository,FavoriteRepository favoriteRepository) {
        this.shopRepository = shopRepository;     
        this.categoryRepository = categoryRepository;
        this.reviewRepository= reviewRepository;
        this.favoriteRepository = favoriteRepository;
        
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
	    public String show(@PathVariable(name = "id") Integer id, Model model,
				@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		  
	        Shop shop = shopRepository.getReferenceById(id);
	        boolean isFavorite = false; // 初期値として false をセット
	        
	        if (userDetailsImpl != null) {
				Integer userId = userDetailsImpl.getUser().getId();
				Optional<Favorite> optionalFavorite = favoriteRepository.findByShopIdAndUserId(id, userId);

				if (optionalFavorite.isPresent()) {
					model.addAttribute("favorite", optionalFavorite.get());
					isFavorite = true; // Favorite が存在する場合は true
				}
			}
	        
	        
	        
	        List<Review> newReview= reviewRepository.findByShopIdOrderByCreatedAtDesc(id);
	        
	        List<Review> displayedReviews = newReview.size() > 6 ? newReview.subList(0, 6) : newReview;
	        
	        model.addAttribute("isFavorite", isFavorite); // boolean 値を必ず渡す
	        model.addAttribute("newReview",newReview);
	        model.addAttribute("displayedReviews", displayedReviews);
	        model.addAttribute("totalReviewCount", newReview.size());
	        model.addAttribute("shop", shop);         
	        model.addAttribute("reservationInputForm", new ReservationInputForm());
	        
	        
	        return "shops/show";
	    }    
	  
	  @GetMapping("/{id}/closedDays")
	    public ResponseEntity<?> getClosedDays(@PathVariable (name = "id") Integer id) {
	        Optional<Shop> shop = shopRepository.findById(id);
	        
	        if (shop.isPresent()) {
	            String closedDay = shop.get().getClosedDay();
	            Map<String, Integer> daysOfWeek = Map.of(
	                "Sunday", 0,
	                "Monday", 1,
	                "Tuesday", 2,
	                "Wednesday", 3,
	                "Thursday", 4,
	                "Friday", 5,
	                "Saturday", 6
	            );
	            Integer closedDayNumber = daysOfWeek.get(closedDay);

	            return ResponseEntity.ok(closedDayNumber);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shop not found");
	        }
	    }
	  
}