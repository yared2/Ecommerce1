package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IplayerRepo extends MongoRepository<Player, Integer> {

}
