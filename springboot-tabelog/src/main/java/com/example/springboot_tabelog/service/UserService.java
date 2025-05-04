package com.example.springboot_tabelog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot_tabelog.entity.Role;
import com.example.springboot_tabelog.entity.User;
import com.example.springboot_tabelog.from.PasswordEditForm;
import com.example.springboot_tabelog.from.SignupForm;
import com.example.springboot_tabelog.from.UserEditForm;
import com.example.springboot_tabelog.repository.RoleRepository;
import com.example.springboot_tabelog.repository.UserRepository;
import com.example.springboot_tabelog.repository.VerificationTokenRepository;

@Service
public class UserService {
	 private final UserRepository userRepository;
	    private final RoleRepository roleRepository;
	    private final PasswordEncoder passwordEncoder;
	    private final VerificationTokenRepository verificationTokenRepository;
	    
	    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,VerificationTokenRepository verificationTokenRepository) {
	        this.userRepository = userRepository;
	        this.roleRepository = roleRepository;        
	        this.passwordEncoder = passwordEncoder;
	        this.verificationTokenRepository =verificationTokenRepository;
	    }    
	    
	    @Transactional
	    public User create(SignupForm signupForm) {
	        User user = new User();
	        Role role = roleRepository.findByName("ROLE_GENERAL");
	        
	        user.setName(signupForm.getName());
	        user.setFurigana(signupForm.getFurigana());
	        user.setPostalCode(signupForm.getPostalCode());
	        user.setAddress(signupForm.getAddress());
	        user.setPhoneNumber(signupForm.getPhoneNumber());
	        user.setEmail(signupForm.getEmail());
	        user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
	        user.setRole(role);
	        user.setEnabled(false);        
	        
	        return userRepository.save(user);
}
	    
	    @Transactional
	    public void update(UserEditForm userEditForm) {
	        User user = userRepository.getReferenceById(userEditForm.getId());
	        
	        user.setName(userEditForm.getName());
	        user.setFurigana(userEditForm.getFurigana());
	        user.setPostalCode(userEditForm.getPostalCode());
	        user.setAddress(userEditForm.getAddress());
	        user.setPhoneNumber(userEditForm.getPhoneNumber());
	        user.setEmail(userEditForm.getEmail());  
	        
	        userRepository.save(user);
	    }    
	    
	    @Transactional
	    public void updatePassword(PasswordEditForm passwordEditForm ) {
	        User user = userRepository.getReferenceById(passwordEditForm.getId());
	        
	        user.setPassword(passwordEncoder.encode(passwordEditForm.getPassword()));
	       
	        
	        userRepository.save(user);
	    }    
	    
	    @Transactional
	    public void updatePasswordTokendelete(Integer userId) {        

		    // 関連トークンの削除
		    verificationTokenRepository.deleteAllByUserId(userId);  
		      
	    } 
	    
		  @Transactional
		    public void delete(Integer userId) {        

			    // 関連トークンの削除
			    verificationTokenRepository.deleteAllByUserId(userId);  
			   
			   userRepository.deleteById(userId);
			      
		    }
		  
		  @Transactional
			public void updateRole(User user, String roleName) {
				Role role = roleRepository.findByName(roleName);
				user.setRole(role);
				userRepository.save(user);
			}
		  
		  @Transactional
		    public void createStripeCustomer(User user, String stripeId) {
		        user.setPaidMember(stripeId);
		        userRepository.save(user);
		    }
		  
		// 認証情報のロールを更新する
			public void refreshAuthenticationByRole(String newRole) {
				// 現在の認証情報を取得する
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				// 新しい認証情報を作成する
				List<SimpleGrantedAuthority> authorities = new ArrayList<>();
				authorities.add(new SimpleGrantedAuthority(newRole));
				Authentication newAuth = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
						authentication.getCredentials(), authorities);

				// 認証情報を更新する
				SecurityContextHolder.getContext().setAuthentication(newAuth);
			}
		  
		  
	    
	 // メールアドレスが登録済みかどうかをチェックする
	    public boolean isEmailRegistered(String email) {
	        User user = userRepository.findByEmail(email);  
	        return user != null;
	    }    
	    
	    // パスワードとパスワード（確認用）の入力値が一致するかどうかをチェックする
	    public boolean isSamePassword(String password, String passwordConfirmation) {
	        return password.equals(passwordConfirmation);
	    }     
	    
	    // ユーザーを有効にする
	    @Transactional
	    public void enableUser(User user) {
	        user.setEnabled(true); 
	        userRepository.save(user);
	    }    
	    
	    // メールアドレスが変更されたかどうかをチェックする
	    public boolean isEmailChanged(UserEditForm userEditForm) {
	        User currentUser = userRepository.getReferenceById(userEditForm.getId());
	        return !userEditForm.getEmail().equals(currentUser.getEmail());      
	    }  
	}
