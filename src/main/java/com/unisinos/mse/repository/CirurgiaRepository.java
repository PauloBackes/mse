package com.unisinos.mse.repository;

import com.unisinos.mse.entity.CirurgiaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CirurgiaRepository extends MongoRepository<CirurgiaEntity, String> {
    CirurgiaEntity findCirurgiaById(String id);
}
