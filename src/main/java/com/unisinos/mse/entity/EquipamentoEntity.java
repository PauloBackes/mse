package com.unisinos.mse.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EquipamentoEntity {
    private String descricao;
    private String patrimonio;
    private String codigo;
}