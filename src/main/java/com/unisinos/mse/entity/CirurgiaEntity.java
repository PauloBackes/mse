package com.unisinos.mse.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "cirurgia")
public class CirurgiaEntity implements Serializable {

    @Transient
    private static final String NOME_SEQUENCE = "cirurgia_sequence";

    @Id
    private Integer id;
    private Boolean ativo;
    private String descricao;
    private String sala;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    @Field("equipamentos")
    private List<EquipamentoEntity> equipamento;
    @Field("materiais")
    private List<MaterialEntity> material;
}

