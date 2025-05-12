package com.example.springboot_tabelog.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springboot_tabelog.entity.Review;
import com.example.springboot_tabelog.entity.Shop;
import com.example.springboot_tabelog.entity.User;
import com.example.springboot_tabelog.from.ReviewEditForm;
import com.example.springboot_tabelog.from.ReviewRegisterForm;
import com.example.springboot_tabelog.repository.ReviewRepository;
import com.example.springboot_tabelog.repository.ShopRepository;
import com.example.springboot_tabelog.security.UserDetailsImpl;
import com.example.springboot_tabelog.service.ReviewService;

@Controller
@RequestMapping("/shops/{shopId}/reviews")
public class ReviewController {
	
	private ReviewService reviewService;
	private ShopRepository shopRepository;
	private ReviewRepository reviewRepository;

	public ReviewController(
			ReviewService reviewService,
			ShopRepository shopRepository,
			ReviewRepository reviewRepository) {
		this.reviewService = reviewService;
		this.shopRepository = shopRepository;
		this.reviewRepository = reviewRepository;
	}

	@GetMapping
	public String index(@PathVariable(name = "shopId") Integer shopId,
			@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable, Model model) {
		Shop shop = shopRepository.getReferenceById(shopId);
		Page<Review> reviewPage = reviewRepository.findByShopIdOrderByCreatedAtDesc(shopId, pageable);
		model.addAttribute("shop",shop);
		model.addAttribute("reviewPage",reviewPage);

		return "reviews/index";
	}

	@GetMapping("/register")
	public String register(@PathVariable(name = "shopId") Integer shopId, Model model) {
		Shop shop = shopRepository.getReferenceById(shopId);

		model.addAttribute("shop", shop);
		model.addAttribute("reviewRegisterForm", new ReviewRegisterForm());

		return "reviews/register";

	}

	@PostMapping("/create")
	public String create(@PathVariable(name = "shopId") Integer shopId,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			@ModelAttribute @Validated ReviewRegisterForm reviewRegisterForm, BindingResult bindingResult,
			RedirectAttributes redirectAttributes,
			Model model) {
		if (bindingResult.hasErrors()) {
			
			return "reviews/register";
		}
		
		reviewRegisterForm.setShopId(shopId);
		
		User user = userDetailsImpl.getUser();
		Integer userId = user.getId();
		reviewRegisterForm.setUserId(userId);
		
		reviewService.create(reviewRegisterForm);
		
	    // shopId をリダイレクトの際に URL 変数として追加
	    redirectAttributes.addAttribute("shopId", shopId);
		
		redirectAttributes.addFlashAttribute("successMessage","レビュー内容を投稿しました。");
		
		return "redirect:/shops/{shopId}/reviews";
	}

	@GetMapping("/{reviewId}/edit")
	public String edit(@PathVariable(name = "shopId") Integer shopId,
			@PathVariable(name = "reviewId") Integer reviewId,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			Model model) {
		Shop shop = shopRepository.getReferenceById(shopId);
		Review review = reviewRepository.getReferenceById(reviewId);
		User user = userDetailsImpl.getUser();
		ReviewEditForm reviewEditForm = new ReviewEditForm(review.getId(), shop.getId(),user.getId(),  review.getScore(), review.getContent());

		model.addAttribute("shop", shop);
		model.addAttribute("review", review);
		model.addAttribute("reviewEditForm", reviewEditForm);

		return "reviews/edit";
	}

	@PostMapping("/{reviewId}/update")
	public String update(@PathVariable(name = "shopId") Integer shopId,
			@PathVariable(name = "reviewId") Integer reviewId,
			@ModelAttribute @Validated ReviewEditForm reviewEditForm,
			BindingResult bindingResult,RedirectAttributes redirectAttributes ,
			Model model) {
		if (bindingResult.hasErrors()) {

			return "reviews/edit";
		}
		

		reviewEditForm.setShopId(shopId);
		reviewEditForm.setId(reviewId);
		
		model.addAttribute("shop", shopId);
		model.addAttribute("review", reviewId);
		
		reviewService.update(reviewEditForm);
		
		// shopId をリダイレクトの際に URL 変数として追加
		redirectAttributes.addAttribute("shopId" , shopId);
		redirectAttributes.addAttribute("reviewId" , reviewId);
		redirectAttributes.addFlashAttribute("successMessage","レビュー内容を編集しました。");
		

		return "redirect:/shops/{shopId}/reviews";

	}

	@PostMapping("/{reviewId}/delete")
	public String delete(@PathVariable(name = "reviewId") Integer reviewId,@PathVariable(name = "shopId") Integer shopId, RedirectAttributes redirectAttributes,Model model) {
		Shop shop = shopRepository.getReferenceById(shopId);
		Review review = reviewRepository.getReferenceById(reviewId);
		reviewRepository.deleteById(reviewId);
		
		model.addAttribute("shop", shop);
		model.addAttribute("review", review);
		
		redirectAttributes.addFlashAttribute("successMessage", "レビューを削除しました");

		return "redirect:/shops/{shopId}/reviews";
	}


}
