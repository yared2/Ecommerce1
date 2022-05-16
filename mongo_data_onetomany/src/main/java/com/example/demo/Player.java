package com.example.demo;

import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Document
@Setter
@Getter
@AllArgsConstructor
public class Player {
	@Id
	private Integer pid;
	private String pname;
    private String paddrs;
	private Set<Sport> sport;
	private Map<String,Medal> medals;
	@Override
	public String toString() {
		return "Player [pid=" + pid + ", pname=" + pname + ", paddrs=" + paddrs ;
	}

	
	

}
