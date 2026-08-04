package com.capstone.backend.repository;

import com.capstone.backend.model.State;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StateRepository extends MongoRepository<State, String> {
}
