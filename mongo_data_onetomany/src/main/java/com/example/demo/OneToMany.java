package com.example.demo;

import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class OneToMany implements CommandLineRunner{
	@Autowired
	private SportMgmnt sportMgmnt;

	@Override
	public void run(String... args) throws Exception {
		Sport sport1 = new Sport(new Random().nextInt(1000),"badminton",new String[]{"racket","sock","net"});
		Sport sport2= new Sport(new Random().nextInt(1000),"tennis",new String[]{"racket","t.ball","net"});
		
		Medal medal1= new Medal("olympic-gold ","cwg-japan","Tokyo-olympic","tokyo");
		Medal medal2= new Medal("cwg-silver ","silver","cwg-japan","tokyo");
		
		Player player = new Player(new Random().nextInt(1000),"sindhu","hyd",
				Set.of(sport1,sport2),Map.of("medal1",medal1,"medal2",medal2));
		
		
		System.out.println(sportMgmnt.registerPlayer(player));
		
	}

}
