package com.example.springboot_tabelog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot_tabelog.entity.Review;
import com.example.springboot_tabelog.entity.Shop;
import com.example.springboot_tabelog.entity.User;
import com.example.springboot_tabelog.from.ReviewEditForm;
import com.example.springboot_tabelog.from.ReviewRegisterForm;
import com.example.springboot_tabelog.repository.ReviewRepository;
import com.example.springboot_tabelog.repository.ShopRepository;
import com.example.springboot_tabelog.repository.UserRepository;
@Service
public class ReviewService {
	
	//reviewテーブルの値をを利用・登録する
		//shopテーブルのIDを利用する
		//userテーブルのIDを利用する
		
		
		private final ReviewRepository reviewRepository;
		private final ShopRepository shopRepository;
		private final UserRepository userRepository;
		


		public ReviewService(ReviewRepository reviewRepository,
				ShopRepository shopRepository,
				UserRepository userRepository) {
			this.reviewRepository = reviewRepository;
			this.shopRepository = shopRepository;
			this.userRepository = userRepository;
		}

		//新規レビューをDBに保存 //ReviewInputForm ←新規投稿のForm
		@Transactional
		//レビューの登録処理を行うcreate()メソッドを定義　※コントローラから呼び出して使う
		public void create(ReviewRegisterForm reviewRegisterForm) {
		    Review review = new Review();
		    
		    //reviewRegisterFormから受け取った民宿ID,ユーザーIDを各エンティティデータと照らし合わせる
		    Shop shop = shopRepository.getReferenceById(reviewRegisterForm.getShopId());
		    User user = userRepository.getReferenceById(reviewRegisterForm.getUserId());
		    
		    //フォームの入力内容を受け取る
		    review.setShop(shop);
		    review.setUser(user);
		    review.setScore(reviewRegisterForm.getScore());
		    review.setContent(reviewRegisterForm.getContent());

		    reviewRepository.save(review);
		}
		
		@Transactional
		//レビューの編集処理を行うupdate()メソッドを定義　※コントローラから呼び出して使う
		public void update(ReviewEditForm reviewEditForm) {
			Review review = reviewRepository.getReferenceById(reviewEditForm.getId());
			Shop shop = shopRepository.getReferenceById(reviewEditForm.getShopId());
		    User user = userRepository.getReferenceById(reviewEditForm.getUserId());
		    
		    //フォームの入力内容を受け取る
		    review.setShop(shop);
		    review.setUser(user);
		    review.setScore(reviewEditForm.getScore());
		    review.setContent(reviewEditForm.getContent());

		    reviewRepository.save(review);
		}

}
