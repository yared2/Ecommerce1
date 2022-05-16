package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext cnt = SpringApplication.run(DemoApplication.class, args);
		CustomrtMgmService bean = cnt.getBean("custService",CustomrtMgmService.class);
		
		System.out.println(bean.registerCustomer(new CustomerDTO(231, "asmara", 443.5f)));
		cnt.close();
		
	}

}
