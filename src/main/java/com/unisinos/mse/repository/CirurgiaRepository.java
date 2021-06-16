package com.unisinos.mse.repository;

import com.unisinos.mse.entity.CirurgiaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CirurgiaRepository extends MongoRepository<CirurgiaEntity, Integer> {
    CirurgiaEntity findCirurgiaById(Integer id);

    CirurgiaEntity save(CirurgiaEntity cirurgiaEntity);
    @Query("{ 'ativo' : ?0}")
    List<CirurgiaEntity> findAllByAtivo(Boolean ativo, Sort sort);

    @Query("{ 'ativo' : ?0}")
    List<CirurgiaEntity> findAllByAtivo2(Boolean ativo, Pageable pageable);
}
