package com.example.demo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Document
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Customer {
	@Id
	private String id;
	@NonNull
	private String cno;
	@NonNull
	private String cname;
	@NonNull
	private float billAmt;

}
