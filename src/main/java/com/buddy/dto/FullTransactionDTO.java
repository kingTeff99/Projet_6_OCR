package com.buddy.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
@Data @AllArgsConstructor @NoArgsConstructor@Builder
public class FullTransactionDTO {
	
	private Long id;
	
	private Double amount;
	
    private Long userSenderId;
    
    private Long userReceiverId;
	
    private Long bankSenderId;
    
    private Long bankReceiverId;
    
    private Double fees;
	
	private String description;

}
