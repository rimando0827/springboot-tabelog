package com.example.springboot_tabelog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springboot_tabelog.entity.User;
import com.example.springboot_tabelog.entity.VerificationToken;
import com.example.springboot_tabelog.event.ResetEventPublisher;
import com.example.springboot_tabelog.event.SignupEventPublisher;
import com.example.springboot_tabelog.from.PasswordEditForm;
import com.example.springboot_tabelog.from.ResetEditForm;
import com.example.springboot_tabelog.from.ResetForm;
import com.example.springboot_tabelog.from.SignupForm;
import com.example.springboot_tabelog.service.ResetService;
import com.example.springboot_tabelog.service.UserService;
import com.example.springboot_tabelog.service.VerificationTokenService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthController {
	private final UserService userService;
	private final ResetService resetService;
	private final SignupEventPublisher signupEventPublisher;
	private final ResetEventPublisher resetEventPublisher;
	private final VerificationTokenService verificationTokenService;

	public AuthController(UserService userService, SignupEventPublisher signupEventPublisher,
			VerificationTokenService verificationTokenService, ResetService resetService,
			ResetEventPublisher resetEventPublisher) {
		this.userService = userService;
		this.signupEventPublisher = signupEventPublisher;
		this.verificationTokenService = verificationTokenService;
		this.resetService = resetService;
		this.resetEventPublisher = resetEventPublisher;
	}

	@GetMapping("/login")
	public String login() {
		return "auth/login";
	}

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("signupForm", new SignupForm());
		return "auth/signup";
	}

	@PostMapping("/signup")
	public String signup(@ModelAttribute @Validated SignupForm signupForm, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		// メールアドレスが登録済みであれば、BindingResultオブジェクトにエラー内容を追加する
		if (userService.isEmailRegistered(signupForm.getEmail())) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです。");
			bindingResult.addError(fieldError);
		}

		// パスワードとパスワード（確認用）の入力値が一致しなければ、BindingResultオブジェクトにエラー内容を追加する
		if (!userService.isSamePassword(signupForm.getPassword(), signupForm.getPasswordConfirmation())) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "password", "パスワードが一致しません。");
			bindingResult.addError(fieldError);
		}

		if (bindingResult.hasErrors()) {
			return "auth/signup";
		}

		User createdUser = userService.create(signupForm);
		String requestUrl = new String(httpServletRequest.getRequestURL());
		signupEventPublisher.publishSignupEvent(createdUser, requestUrl);
		redirectAttributes.addFlashAttribute("successMessage",
				"ご入力いただいたメールアドレスに認証メールを送信しました。メールに記載されているリンクをクリックし、会員登録を完了してください。");

		return "redirect:/";
	}

	@GetMapping("/login/verify")
	public String verify(@RequestParam(name = "token") String token, Model model) {
		VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);

		if (verificationToken != null) {
			User user = verificationToken.getUser();
			userService.enableUser(user);
			String successMessage = "会員登録が完了しました。";
			model.addAttribute("successMessage", successMessage);
		} else {
			String errorMessage = "トークンが無効です。";
			model.addAttribute("errorMessage", errorMessage);
		}

		return "auth/verify";
	}

	@GetMapping("/reset")
	public String reset(Model model) {
		model.addAttribute("resetForm", new ResetForm());
		return "auth/reset";
	}

	@PostMapping("/reset")
	public String reset(@ModelAttribute @Validated ResetForm resetForm, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		User resetUser = resetService.findUserByEmail(resetForm.getEmail());
		if (!resetService.isSameEmail(resetForm.getEmail())) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "こちらのメールアドレスは登録されてません");
			bindingResult.addError(fieldError);

			return "auth/reset";
		}

		// リクエストURLの作成
		String protocol = httpServletRequest.getScheme(); // http
		String host = httpServletRequest.getServerName(); // localhost
		int port = httpServletRequest.getServerPort(); // 8080
		String contextPath = httpServletRequest.getContextPath(); // /app

		// こちらでは、ポートが80や443以外の場合にポートを含めるようにしています。
		String baseUrl;
		if (port != 80 && port != 443) {
			baseUrl = String.format("%s://%s:%d%s", protocol, host, port, contextPath);
		} else {
			baseUrl = String.format("%s://%s%s", protocol, host, contextPath);
		}

		String requestUrl = baseUrl + "/reset/password"; // 必要なエンドポイントに変更

		resetEventPublisher.publishResetEvent(resetUser, requestUrl);
		redirectAttributes.addFlashAttribute("successMessage", "メールアドレスにURLをお送りしました");

		return "redirect:/reset";
	}

	@GetMapping("/reset/password")
	public String passwordResetEdit(@RequestParam(name = "token") String token, Model model,
			ResetEditForm resetEditForm,BindingResult bindingResult) {
		VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);

		if (verificationToken != null) {
			ResetEditForm resetEditForm1 = new ResetEditForm(token, null, null);

			model.addAttribute("resetEditForm", resetEditForm1);
			return "auth/password";

			

		} else {

			String errorMessage = "トークンが無効です。";
			model.addAttribute("errorMessage", errorMessage);
			return "auth/reset";

		}

	}

	@PostMapping("/reset/password")
	public String passwordResetUpdate(Model model, ResetEditForm resetEditForm,BindingResult bindingResult,RedirectAttributes redirectAttributes) {
		VerificationToken verificationToken = verificationTokenService.getVerificationToken(resetEditForm.getToken());
		User user = verificationToken.getUser();
		PasswordEditForm passwordEditForm = new PasswordEditForm(user.getId(), resetEditForm.getPassword());
		
		if (!userService.isSamePassword(resetEditForm.getPassword(), resetEditForm.getPasswordConfirmation())) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "password", "パスワードが一致しません。");
			bindingResult.addError(fieldError);
			
			return "auth/password";
		}
		
		else {

		userService.updatePassword(passwordEditForm);
		userService.updatePasswordTokendelete(user.getId());
		redirectAttributes.addFlashAttribute("successMessage","パスワード変更致しました");
		
		return "redirect:/login";
		}

		

	}

}
