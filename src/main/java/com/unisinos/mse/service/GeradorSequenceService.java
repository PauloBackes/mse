package com.unisinos.mse.service;

import com.unisinos.mse.entity.SequenceEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
@AllArgsConstructor
public class GeradorSequenceService {


    private MongoOperations mongoOperations;

    public Integer getSequence(String sequenceName) {
        //Pega sequence no banco
        Query query = new Query(Criteria.where("id").is(sequenceName));
        //Atualiza a sequence
        Update update = new Update().inc("sequence", 1);
        //Modifica o documento db_sequence
        SequenceEntity contador = mongoOperations
                .findAndModify(query,
                        update, options().returnNew(true).upsert(true),
                        SequenceEntity.class);

        return !Objects.isNull(contador) ? contador.getSequence() : 1;
    }
}
