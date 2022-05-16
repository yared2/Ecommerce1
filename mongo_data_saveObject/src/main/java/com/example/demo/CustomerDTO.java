package com.example.demo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTO implements Serializable{
	
	private Integer cno;
	private String cname;
	private Float billAmt;

}
