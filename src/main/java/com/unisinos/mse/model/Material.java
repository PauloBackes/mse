package com.unisinos.mse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Material {
    private String descricao;
    private String codigo;
    private Integer quantidade;//TODO remover esse campo
    private Boolean validado;
}
