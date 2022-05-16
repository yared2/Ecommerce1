package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class SportMgmnt {
	
	@Autowired
	private IplayerRepo playerRepo;
	@Autowired
	private Isport sportRepo;
	@Autowired
	private Imedal medalrepo;
	
	
	public String registerPlayer(Player player) {
		System.out.println("========in the service===========");
		
	return "player with sport and medal info is saved"+playerRepo.save(player);
	}

	public List<Player> fatchAllPlayer(){
		return playerRepo.findAll();
	}
	
	
}
