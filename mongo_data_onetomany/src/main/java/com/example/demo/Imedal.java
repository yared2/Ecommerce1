package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface Imedal extends MongoRepository<Medal, String> {

}
