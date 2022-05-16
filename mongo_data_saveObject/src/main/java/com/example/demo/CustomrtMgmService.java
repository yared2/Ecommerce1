package com.example.demo;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Service("custService")
public class CustomrtMgmService{
	
	@Autowired
	private  CustomerRepo custRepo;
	
	public String registerCustomer(CustomerDTO dto) {
		Customer doc = new Customer();
		BeanUtils.copyProperties(dto, doc);
		Customer save = custRepo.insert(doc);
		
		return save.getId()+"Document is saved with id";
	}

}
