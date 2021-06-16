package com.unisinos.mse.facade;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unisinos.mse.entity.CirurgiaEntity;
import com.unisinos.mse.entity.EquipamentoEntity;
import com.unisinos.mse.entity.MaterialEntity;
import com.unisinos.mse.mapper.CirurgiaMapper;
import com.unisinos.mse.model.Cirurgia;
import com.unisinos.mse.model.RemoverItem;
import com.unisinos.mse.service.CirurgiaService;
import com.unisinos.mse.service.GeradorSequenceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CirurgiaFacade {
    CirurgiaService cirurgiaService;
    GeradorSequenceService geradorSequenceService;

    public Cirurgia buscarCirurgiaPeloId(Integer id) {
        return cirurgiaService.buscarCirurgiaPeloId(id);
    }

    public List<Cirurgia> buscarTodasCirurgias() {
        return cirurgiaService.buscarTodasCirurgias();
    }

    public List<Cirurgia> buscarProximasCirurgias() {
        return cirurgiaService.buscarProximasCirurgias();
    }

    public Cirurgia atualizarInstrumentosValidados(Integer cirurgiaId,
                                                   String[] equipamentosSelecionados,
                                                   String[] materiaisSelecionados) {

        return cirurgiaService.atualizarInstrumentosValidados(cirurgiaId,
                equipamentosSelecionados,
                materiaisSelecionados);
    }

    public void gravarCirurgia() {

        List<CirurgiaEntity> cirurgias = List.of(CirurgiaEntity.builder()
                        .id(geradorSequenceService.getSequence("cirurgia_sequence"))
                        .ativo(Boolean.TRUE)
                        .descricao("Cirurgia de tendão")
                        .sala("209")
                        .dataInicio(LocalDateTime.now())
                        .dataFim(LocalDateTime.now())
                        .equipamento(List.of(EquipamentoEntity.builder()
                                .descricao("Raio x")
                                .patrimonio("123456")
                                .codigo("002")
                                .validado(false)
                                .build()))
                        .material(List.of(MaterialEntity.builder()
                                .descricao("Algodão")
                                .codigo("56")
                                .quantidade(10)
                                .validado(true)
                                .build()))
                        .build(),
                CirurgiaEntity.builder()
                        .id(geradorSequenceService.getSequence("cirurgia_sequence"))
                        .ativo(Boolean.TRUE)
                        .descricao("Cirurgia de baço")
                        .sala("291")
                        .dataInicio(LocalDateTime.now())
                        .dataFim(LocalDateTime.now())
                        .equipamento(List.of(EquipamentoEntity.builder()
                                .descricao("Raio x")
                                .patrimonio("123456")
                                .codigo("002")
                                .validado(false)
                                .build()))
                        .material(List.of(MaterialEntity.builder()
                                .descricao("Algodão")
                                .codigo("56")
                                .quantidade(10)
                                .validado(true)
                                .build()))
                        .build()
        );

        cirurgias.stream().forEach(cirurgiaService::atualizarCirurgia);
    }


    public Cirurgia removerItem(RemoverItem removerItem) {
        if (("Equipamento").equals(removerItem.getTipoItem())) {
            var cirurgia = cirurgiaService.buscarCirurgiaPeloId(removerItem.getIdCirurgia());
            cirurgia.setEquipamento(cirurgia.getEquipamento().stream()
                    .filter(equipamento -> !equipamento.getCodigo().equals(removerItem.getCodigoItem()))
                    .collect(Collectors.toList()));

            return cirurgiaService.atualizarCirurgia(CirurgiaMapper.mapToCirurgiaEntity(cirurgia));

        } else if (("Material").equals(removerItem.getTipoItem())) {
            var cirurgia = cirurgiaService.buscarCirurgiaPeloId(removerItem.getIdCirurgia());
            cirurgia.setMaterial(cirurgia.getMaterial().stream()
                    .filter(material -> !material.getCodigo().equals(removerItem.getCodigoItem()))
                    .collect(Collectors.toList()));

            return cirurgiaService.atualizarCirurgia(CirurgiaMapper.mapToCirurgiaEntity(cirurgia));
        }
        return null;
    }
}
