package com.unisinos.mse.repository;

import com.unisinos.mse.model.Cirurgia;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CirurgiaRepository extends MongoRepository<Cirurgia, String> {
    Cirurgia findCirurgiaById(String id);
}
