package com.unisinos.mse.mapper;

import com.unisinos.mse.entity.EquipamentoEntity;
import com.unisinos.mse.model.Equipamento;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EquipamentoMapper {

    public static List<Equipamento> mapToEquipamentoList(List<EquipamentoEntity> equipamentoEntity) {
        if (!ObjectUtils.isEmpty(equipamentoEntity)) {
            return equipamentoEntity.stream()
                    .map(EquipamentoMapper::mapToEquipamento)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private static Equipamento mapToEquipamento(EquipamentoEntity equipamentoEntity) {
        return Equipamento.builder()
                .descricao(equipamentoEntity.getDescricao())
                .patrimonio(equipamentoEntity.getPatrimonio())
                .codigo(equipamentoEntity.getCodigo())
                .validado(equipamentoEntity.getValidado())
                .build();
    }
}
