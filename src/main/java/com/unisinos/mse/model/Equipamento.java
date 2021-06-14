package com.unisinos.mse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Equipamento {
    private String descricao;
    private String patrimonio;
    private String codigo;
    private Boolean validado;
    private List<String> nomeEquipamento;
}
