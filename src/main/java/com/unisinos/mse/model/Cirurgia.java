package com.unisinos.mse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "cirurgia")
public class Cirurgia {
    @Id
    private String id;
    private String nomeCirurgia;
    @Field("equipamentos")
    private Equipamento equipamento;
    @Field("materiais")
    private Material material;
    //private String descricao;
}
