package com.example.demo.service;


import java.time.LocalDate;
import java.util.List;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.model.Accounting;
import com.example.demo.repository.AccountingRepository;

@Service
public class AccountingService {
	

	private final AccountingRepository accountingRepository;
	private final UserService userService;
	
	public AccountingService(AccountingRepository accountingRepository, UserService userService) {
		this.accountingRepository = accountingRepository;
		this.userService = userService;
	}
	
	private Long getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        return user.getId();
    }
	
	public List<Accounting> getAllItem(){
		Long userId = getCurrentUserId();
		return accountingRepository.findByUserId(userId);
	}
	
	public Accounting getOneItemById(Long id){
		return accountingRepository.findById(id).orElse(null);
	}
	
	public void saveItem(Accounting item) {
		Long userId = getCurrentUserId();
	    item.setUserId(userId);
		accountingRepository.save(item);
	}
	
	public void updateItem(Long id, Accounting item) {
		Accounting existItem = getOneItemById(id);
		if(existItem != null) {
			existItem.setAmount(item.getAmount());
			existItem.setBalance(item.getBalance());
			existItem.setCategory(item.getCategory());
			existItem.setDate(item.getDate());
			existItem.setName(item.getName());
			existItem.setNote(item.getNote());
			accountingRepository.save(existItem);
		}
	}
	
	public void deleteItemById(Long id) {
		accountingRepository.deleteById(id);
	}
	
	public List<Accounting> getRecordsBetweenDates(LocalDate startDate, LocalDate endDate) {
        return accountingRepository.findByDateBetween(startDate, endDate);
    }
	
	public List<Accounting> getLatestItems() {
		Long userId = getCurrentUserId();
	    List<Accounting> user = accountingRepository.findLatestRecord(userId);
	    return user;
	}
	
}
