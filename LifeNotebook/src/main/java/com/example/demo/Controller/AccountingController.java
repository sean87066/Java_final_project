package com.example.demo.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Accounting;
import com.example.demo.service.AccountingService;





@Controller
public class AccountingController {
	
	@Autowired
	private final AccountingService accountingService;

	
	public AccountingController(AccountingService accountingService) {
		this.accountingService = accountingService;
	}
	
	@GetMapping("/accounting")
	public String ShowAccountingPage(Model model) {
		// 呼叫新的服務方法來取得最新50筆資料
	    List<Accounting> items = accountingService.getLatestItems();
	    model.addAttribute("Accounting", items);
	    model.addAttribute("dataCount", items.size());
	    return "accounting";
	}
	
	
	@GetMapping("/accounting/addData")
	public String showAddData(Model model) {
		Accounting item = new Accounting();
		model.addAttribute("Accounting", item);
		return "add-data-form";
	}
	
	//新增項目
	@PostMapping("/accounting/addData")
    public String addData(@ModelAttribute("Accounting") Accounting accounting) {
        accountingService.saveItem(accounting);
        return "redirect:/accounting";
    }
	
	//編輯項目
	@GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Accounting accounting = accountingService.getOneItemById(id);
        
//        LocalDate dateString = accounting.getDate(); // 返回 "2025/01/04"
//        // 替換格式
//        String formattedDate = dateString.toString().replace("/", "-");
//        // 轉換為 LocalDate
//        LocalDate localDate = LocalDate.parse(formattedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        // 設定回 accounting
//        accounting.setDate(localDate);
//        System.out.println(accounting.getDate());
        
        model.addAttribute("tempAccounting", accounting);
        return "edit-data-form";
    }
	
	//更新項目
    @PostMapping("/edit/{id}")
    public String updateItem(@PathVariable Long id, @ModelAttribute("tempAccounting") Accounting accounting) {
        accountingService.updateItem(id, accounting);
        return "redirect:/accounting";
    }

    //刪除項目
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        accountingService.deleteItemById(id);
        return "redirect:/accounting";
    }
	
    // 顯示搜尋結果頁面
    @GetMapping("/search")
    public String showSearchPage(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            Model model) {

        List<Accounting> results = accountingService.getRecordsBetweenDates(startDate, endDate);
        results = accountingService.getAllItem();
        model.addAttribute("results", results);
        model.addAttribute("dataCount", results.size());
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "search";
    }
    
    
    
    
    

}
