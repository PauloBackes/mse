package com.unisinos.mse.repository;

import com.unisinos.mse.model.Cirurgia;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CirurgiaRepository extends MongoRepository<Cirurgia, String> {

    /*@Query(value="{ 'firstname' : ?0 }", fields="{ 'firstname' : 1, 'lastname' : 1}")
    List<Cirurgia> findLastDocuments(String firstname);*/
}
