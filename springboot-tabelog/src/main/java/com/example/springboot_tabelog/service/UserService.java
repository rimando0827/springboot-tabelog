package com.example.springboot_tabelog.service;

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
