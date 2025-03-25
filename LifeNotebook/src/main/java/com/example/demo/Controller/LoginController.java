package com.example.demo.Controller;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.RegistrationDto;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class LoginController {
	
	@Autowired
    private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping("/")
    public String showWlecomePage() {
        return "welcome";
    }
	
	@GetMapping("/login")
	public String loginPage(HttpServletRequest request, Model model) {
		CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
		if (csrfToken !=null) {
			model.addAttribute("_csrf",csrfToken);
		}
		return "login";
	}
	
	
	 @GetMapping("/register-free-account")
	 public String showRegisterFreeAccountPage(Model model) {
	model.addAttribute("registrationDto", new RegistrationDto());
	     return "register-free-account";
	 }
	
	@GetMapping("/register-paid-account")
    public String showRegisterPaidAccountPage(Model model) {
    	model.addAttribute("registrationDto", new RegistrationDto());
        return "register-paid-account";
    }
	
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	
	
	@PostMapping("/register-free-account")
	public String processRegistrationForm(
			@Valid @ModelAttribute("registrationDto") RegistrationDto registrationDto,
			BindingResult theBindingResult, Model theModel) {

		String userName = registrationDto.getUsername();
		logger.info("Processing registration form for: " + userName);
		
		// form validation
		 if (theBindingResult.hasErrors()){
			 return "register-free-account";
		 }
		 
		 // 檢查使用者名稱是否存在
	    if (userService.isUsernameExists(userName)) {
	        theModel.addAttribute("registrationError", "使用者已存在，請使用其他帳號。");
	        return "register-free-account";
	    }

        userService.registerUser(registrationDto);

        logger.info("Successfully created user: " + userName);

        return "redirect:/login";
	}

	 @GetMapping("/access-denied")
	    public String showAccessDenied() {

	        return "access-denied";
	    }

	
	@PostConstruct
    public void init() {
        if (userService.findByUsername("admin99") == null) {
            User admin = new User();
            admin.setUsername("admin99");
            admin.setPassword("admin99");
            admin.setEmail("admin@demo.com");
            admin.setRole("ADMIN");
            userService.saveUser(admin);
        }
    }
	

}
