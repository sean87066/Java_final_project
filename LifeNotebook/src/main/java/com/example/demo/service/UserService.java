package com.example.demo.service;

import com.example.demo.dto.RegistrationDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public void saveUser(User user) {
        // 編碼密碼
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        // 保存用戶
        userRepository.save(user);
    }
    
    public boolean isUsernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }
    
    public String registerUser(RegistrationDto registrationDto) {
    	if (isUsernameExists(registrationDto.getUsername())) {
            return "Username already exists.";
        }
    	
        User newUser = new User();
        newUser.setUsername(registrationDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        newUser.setEmail(registrationDto.getEmail());
        newUser.setRole("USER"); // 默認角色設為 USER
        userRepository.save(newUser);
        return null; // 註冊成功
    }
    
    public User findByUsername(String username) {
    	return userRepository.findByUsername(username);
    }
    

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    
    // 一般使用者更新密碼
    public boolean userUpdatePassword(String username, String oldPassword, String newPassword) {
    	User user = userRepository.findByUsername(username);
        // 驗證舊密碼
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return false; // 密碼不正確
        }
        // 更新為新密碼
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }
    
    
    public User getOneUserById(Long id){
		return userRepository.findById(id).orElse(null);
	}
    
    // 管理員更新密碼
    public boolean adminUpdatePassword(long id, User theuser) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        user.setEmail(theuser.getEmail());
        user.setRole(theuser.getRole());
        user.setPassword(passwordEncoder.encode(theuser.getPassword()));
        userRepository.save(user);
        return true;
    }
    
    public void deleteItemById(Long id) {
		userRepository.deleteById(id);
	}
    




}
