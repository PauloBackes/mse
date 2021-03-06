package com.unisinos.mse.mapper;

import com.unisinos.mse.entity.CirurgiaEntity;
import com.unisinos.mse.entity.EquipamentoEntity;
import com.unisinos.mse.entity.MaterialEntity;
import com.unisinos.mse.model.Cirurgia;
import com.unisinos.mse.utils.FormatadorDataHora;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CirurgiaMapper {

    public static Cirurgia mapToCirurgia(CirurgiaEntity cirurgiaEntity) {
        return Optional.ofNullable(cirurgiaEntity).map(cirurgia ->
                Cirurgia.builder()
                        .id(cirurgia.getId())
                        .sala(cirurgia.getSala())
                        .descricao(cirurgia.getDescricao())
                        .dataInicio(FormatadorDataHora.formatarData(cirurgia.getDataInicio()))
                        .dataTermino(FormatadorDataHora.formatarData(cirurgia.getDataFim()))
                        .horaInicio(FormatadorDataHora.formatarHora(cirurgia.getDataInicio()))
                        .horaTermino(FormatadorDataHora.formatarHora(cirurgia.getDataFim()))
                        .equipamento(EquipamentoMapper.mapToEquipamentoList(cirurgiaEntity.getEquipamento()))
                        .material(MaterialMapper.mapToMaterialList(cirurgiaEntity.getMaterial()))
                        .ativo(cirurgia.getAtivo())
                        .dataTeste(cirurgia.getDataInicio())
                        .dataTesteFim(cirurgia.getDataFim())
                        .build())
                .orElse(null);
    }

    public static List<Cirurgia> mapToCirurgiaList(List<CirurgiaEntity> cirurgiaEntityList) {
        if (!ObjectUtils.isEmpty(cirurgiaEntityList)) {
            return cirurgiaEntityList.stream()
                    .map(CirurgiaMapper::mapToCirurgia)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public static CirurgiaEntity mapToCirurgiaEntity(Cirurgia cirurgia) {
        if (ObjectUtils.isEmpty(cirurgia)) return null;

        return CirurgiaEntity.builder()
                .id(cirurgia.getId())
                .ativo(cirurgia.getAtivo())
                .descricao(cirurgia.getDescricao())
                .sala(cirurgia.getSala())
                .dataInicio(cirurgia.getDataTeste())
                .dataFim(cirurgia.getDataTesteFim())
                .equipamento(EquipamentoMapper.mapToEquipamentoEntityList(cirurgia.getEquipamento()))
                .material(MaterialMapper.mapToMaterialEntityList(cirurgia.getMaterial()))
                .build();
    }
}
