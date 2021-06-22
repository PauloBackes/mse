package com.unisinos.mse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Cirurgia {
    private Integer id;
    private Boolean ativo;
    private String sala;
    private String descricao;
    private String dataInicio;
    private String dataTermino;
    private String horaInicio;
    private String horaTermino;
    private List<Equipamento> equipamento;
    private List<Material> material;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dataTeste;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dataTesteFim;
    private Boolean todosItensConferidos;
}
