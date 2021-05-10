package com.unisinos.mse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Equipamento {
    private String nome;
    private String patrimonio;
    //private TipoEquipamento tipo;
    //private int quantidade;
}
