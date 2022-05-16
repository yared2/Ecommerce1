package com.example.demo;

import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Document
@Setter
@Getter
@AllArgsConstructor
public class Sport {
	
	@Id
	private Integer sid;
	private String name;
	private String[]kitItems;


	@Override
	public String toString() {
		return "Sport [sid=" + sid + ", name=" + name + ", kitItems=" + Arrays.toString(kitItems) + "]";
	}
	
	

}
