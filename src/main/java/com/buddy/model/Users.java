package com.buddy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Users {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	 
	private String firstName;
	
	private String lastName;
	
	private String username;
	
	private String password;
	
//	@OneToOne(mappedBy = "userId")
//	private BankAccount bankAccount;
//	
//	@OneToMany(mappedBy = "userSenderId")
//	private List<Transaction> transactionSenderId;
//    
//	@OneToMany(mappedBy = "userReceiverId")
//	private List<Transaction> transactionReceiverId;
//	
//	@OneToMany(mappedBy = "userRelatedId")
//	private List<Contact> userRelatedId;
//	
//	@OneToMany(mappedBy = "userRelatingId")
//	private List<Contact> userRelatingId;
	
}
