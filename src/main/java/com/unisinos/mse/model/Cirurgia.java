package com.unisinos.mse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "cirurgia")
public class Cirurgia {
    @Id
    private String id;
    private String descricao;
    private LocalDateTime data;
    @Field("equipamentos")
    private Equipamento equipamento;
    @Field("materiais")
    private Material material;
}
