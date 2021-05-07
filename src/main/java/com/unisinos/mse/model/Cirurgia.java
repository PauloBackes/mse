package com.unisinos.mse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "cirurgia")
public class Cirurgia {
    @Id
    private String id;
    private Equipamento equi;
    private String nome;
    private String descricao;
}
