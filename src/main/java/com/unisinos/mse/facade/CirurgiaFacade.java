package com.unisinos.mse.facade;

import com.unisinos.mse.entity.CirurgiaEntity;
import com.unisinos.mse.entity.EquipamentoEntity;
import com.unisinos.mse.entity.MaterialEntity;
import com.unisinos.mse.mapper.CirurgiaMapper;
import com.unisinos.mse.model.*;
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
        var cirurgias = cirurgiaService.buscarTodasCirurgias();
        setarItensConferidos(cirurgias);
        return cirurgias;
    }

    private void setarItensConferidos(List<Cirurgia> cirurgias) {
        cirurgias.stream().forEach(cirurgia -> {
            Boolean equipamentoNaoValidado = isEquipametoNaoValidado(cirurgia.getEquipamento());

            Boolean materialNaoValidado = isMaterialNaoValidado(cirurgia.getMaterial());

            if (equipamentoNaoValidado || materialNaoValidado) {
                cirurgia.setTodosItensConferidos(Boolean.FALSE);
            } else {
                cirurgia.setTodosItensConferidos(Boolean.TRUE);
            }
        });
    }

    private Boolean isEquipametoNaoValidado(List<Equipamento> equipamentos) {
        return equipamentos.stream().anyMatch(equipamento -> !equipamento.getValidado());
    }

    private Boolean isMaterialNaoValidado(List<Material> materiais) {
        return materiais.stream().anyMatch(material -> !material.getValidado());
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

    public Cirurgia atualizarCirurgia(Cirurgia cirurgia) {
        return cirurgiaService.atualizarCirurgia(CirurgiaMapper.mapToCirurgiaEntity(cirurgia));
    }

    public Cirurgia adicionarItem(AdicionarItem adicionarItem) {
        if (("Equipamento").equals(adicionarItem.getTipoItem())) {
            var cirurgia = cirurgiaService.buscarCirurgiaPeloId(adicionarItem.getIdCirurgia());
            var equipamentos = cirurgia.getEquipamento();

            for (int contador = 0; contador < adicionarItem.getQuantidade(); contador++) {
                equipamentos.add(0, Equipamento.builder()
                        .codigo(" ")
                        .validado(Boolean.FALSE)
                        .descricao(" ")
                        .build());
            }
            cirurgia.setEquipamento(equipamentos);
            return cirurgiaService.atualizarCirurgia(CirurgiaMapper.mapToCirurgiaEntity(cirurgia));

        } else if (("Material").equals(adicionarItem.getTipoItem())) {
            var cirurgia = cirurgiaService.buscarCirurgiaPeloId(adicionarItem.getIdCirurgia());
            var materiais = cirurgia.getMaterial();

            for (int contador = 0; contador < adicionarItem.getQuantidade(); contador++) {
                materiais.add(0, Material.builder()
                        .codigo(" ")
                        .validado(Boolean.FALSE)
                        .descricao(" ")
                        .build());
            }
            cirurgia.setMaterial(materiais);
            return cirurgiaService.atualizarCirurgia(CirurgiaMapper.mapToCirurgiaEntity(cirurgia));
        }
        return null;
    }
}
