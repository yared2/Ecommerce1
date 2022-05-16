package com.example.demo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Document
@Getter
@Setter
@AllArgsConstructor
public class Medal {
	
	@Id
	private String mid;
	private String name;
	private String event;
	private String place;
	@Override
	public String toString() {
		return "Medal [mid=" + mid + ", name=" + name + ", event=" + event + ", place=" + place + "]";
	}
	
	

}
