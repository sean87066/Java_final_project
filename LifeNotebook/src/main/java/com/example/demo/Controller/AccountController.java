package com.example.demo.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;




@Controller
public class AccountController {
	
	@Autowired
	private final UserService userService;
	
	public AccountController(UserService userService) {
		this.userService = userService;
	}
	
	//User show info
    @GetMapping("/change-password")
    public String showInfoPage(Model model) {
     String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(name);
        model.addAttribute("userinfo", user);
     return "change-password";
    }
    
    //User update password
    @PostMapping("/update-password")
    public String UserChangePassword(
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword,
            Model model) {
        // 從 SecurityContextHolder 中獲取當前使用者名稱
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            boolean isUpdated = userService.userUpdatePassword(username, oldPassword, newPassword);

            if (!isUpdated) {
                model.addAttribute("error", "舊有密碼不正確");
                return "change-password";
            }

            model.addAttribute("message", "新密碼更換成功");
            return "change-password";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "change-password";
        }
    }
    

    
    @GetMapping("/system")
    public String ShowUserInfoPage(Model model) {
    	List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("dataCount", users.size());
        return "system";
    }
    
    
    @GetMapping("/system/admin-change-password/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User user = userService.getOneUserById(id);
        model.addAttribute("user", user);
        return "admin-change-password";
    }
    
    @PostMapping("/system/admin-change-password/{id}")
    public String adminChangePassword(
    		@PathVariable("id") long id,
    		@ModelAttribute("user") User user,
            Model model) {
    		System.out.println(user.getPassword());
            userService.adminUpdatePassword(id, user);
            model.addAttribute("message", "成功更換密碼");
            return "admin-change-password";
    }
    
 
    @GetMapping("/system/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        userService.deleteItemById(id);
        return "redirect:/system";
    }

}
