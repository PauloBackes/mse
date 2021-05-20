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
public class Cirurgia {
    private String id;
    private Boolean ativo;
    private String sala;
    private String descricao;
    private String data;
    private String horaInicio;
    private String horaTermino;
    private List<Equipamento> equipamento;
    private List<Material> material;
    private String[] checkbox;
}
