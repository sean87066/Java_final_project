package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Accounting;

public interface AccountingRepository extends JpaRepository<Accounting, Long>{
	
	
	List<Accounting> findByUserId(long userId);
	Page<Accounting> findByUserId(long userId, PageRequest pageRequest);
    List<Accounting> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    // 設定分頁查詢，從最新的資料開始取 50 筆
    @Query(value = "SELECT * FROM accounting WHERE user_id=:userId ORDER BY date DESC LIMIT 50;", nativeQuery = true)
    List<Accounting> findLatestRecord(@Param("userId") Long userId);
}
