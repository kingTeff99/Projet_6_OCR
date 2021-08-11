package com.buddy.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
@Data@AllArgsConstructor@NoArgsConstructor
public class ContactDTO {
	
	 private Long id;
	 
	 private Long userRelatedId;
	 
	 private Long userRelatingId;
	 

}
