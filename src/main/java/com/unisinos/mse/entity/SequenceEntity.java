package com.unisinos.mse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "db_sequence")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SequenceEntity {
    @Id
    private String id;
    private int sequence;
}
