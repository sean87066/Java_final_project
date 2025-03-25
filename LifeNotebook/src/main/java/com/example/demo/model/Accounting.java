package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "accounting")
public class Accounting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	// 對應 DATE
	@Column(name = "date")
	@Temporal(TemporalType.DATE)
	private LocalDate date;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "balance")
	private String balance;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "amount")
	private Double amount;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "userId")
	private long userId;
	
	public Accounting() {
		
	}

	public Accounting(long id, LocalDate date, String category, String balance, String name, Double amount, String note, long userId) {
		super();
		this.id = id;
		this.date = date;
		this.category = category;
		this.balance = balance;
		this.name = name;
		this.amount = amount;
		this.note = note;
		this.userId = userId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	
	@Override
	public String toString() {
		return "Accounting [id=" + id + ", date=" + date + ", category=" + category + ", balance=" + balance + ", name="
				+ name + ", amount=" + amount + ", note=" + note + ", userId=" + userId + "]";
	}


}
