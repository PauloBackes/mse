package com.unisinos.mse.service;

import com.unisinos.mse.entity.CirurgiaEntity;
import com.unisinos.mse.entity.EquipamentoEntity;
import com.unisinos.mse.entity.MaterialEntity;
import com.unisinos.mse.mapper.CirurgiaMapper;
import com.unisinos.mse.model.Cirurgia;
import com.unisinos.mse.repository.CirurgiaRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CirurgiaService {

    CirurgiaRepository cirurgiaRepository;

    public List<Cirurgia> buscarProximasCirurgias() {
        Sort sort = Sort.by(Sort.Direction.ASC, "dataInicio");
        PageRequest pagina = PageRequest.of(0, 20, sort);
        var dataAtual = LocalDateTime.now();

        var cirurgias = cirurgiaRepository.findAllByAtivo2(Boolean.TRUE, pagina)
                .stream().filter(cirurgia ->
                        cirurgia.getDataInicio().getDayOfMonth() >= dataAtual.getDayOfMonth() &&
                                cirurgia.getDataInicio().getMonthValue() >= dataAtual.getMonthValue() &&
                                cirurgia.getDataInicio().getYear() == dataAtual.getYear())
                .collect(Collectors.toList());

        return CirurgiaMapper.mapToCirurgiaList(cirurgias);
    }

    public List<Cirurgia> buscarTodasCirurgias() {
        Sort sort = Sort.by(Sort.Direction.DESC, "dataInicio");
        PageRequest request = PageRequest.of(0, 2, sort);
        return CirurgiaMapper.mapToCirurgiaList(cirurgiaRepository.findAllByAtivo(Boolean.TRUE, sort));
    }

    public Cirurgia buscarCirurgiaPeloId(Integer id) {
        return CirurgiaMapper.mapToCirurgia(cirurgiaRepository.findCirurgiaById(id));
    }

    public Cirurgia atualizarInstrumentosValidados(Integer cirurgiaId,
                                                   String[] equipamentosSelecionados,
                                                   String[] materiaisSelecionados) {

        CirurgiaEntity cirurgiaEntity = cirurgiaRepository.findCirurgiaById(cirurgiaId);

        cirurgiaEntity = resetarInstrumentosValidos(cirurgiaEntity);

        cirurgiaEntity = atualizarMateriaisValidados(cirurgiaEntity, materiaisSelecionados);
        cirurgiaEntity = atualizarEquipamentosValidados(cirurgiaEntity, equipamentosSelecionados);

        return atualizarCirurgia(cirurgiaEntity);
    }

    private CirurgiaEntity atualizarMateriaisValidados(CirurgiaEntity cirurgiaEntity, String[] materiais) {
        if (ObjectUtils.isEmpty(materiais)) return cirurgiaEntity;

        for (String materialSelecionado : materiais) {
            for (MaterialEntity materialAtual : cirurgiaEntity.getMaterial()) {
                if (materialSelecionado.equals(materialAtual.getDescricao())) {
                    materialAtual.setValidado(true);
                }
            }
        }
        return cirurgiaEntity;
    }

    private CirurgiaEntity atualizarEquipamentosValidados(CirurgiaEntity cirurgiaEntity, String[] equipamentos) {
        if (ObjectUtils.isEmpty(equipamentos)) return cirurgiaEntity;

        for (String equipamentoSelecionado : equipamentos) {
            for (EquipamentoEntity equipamentoAtual : cirurgiaEntity.getEquipamento()) {
                if (equipamentoSelecionado.equals(equipamentoAtual.getDescricao())) {
                    equipamentoAtual.setValidado(true);
                }
            }
        }
        return cirurgiaEntity;
    }

    private CirurgiaEntity resetarInstrumentosValidos(CirurgiaEntity cirurgiaEntity) {
        cirurgiaEntity.getEquipamento()
                .stream()
                .forEach(equipamentoEntity -> equipamentoEntity.setValidado(false));

        cirurgiaEntity.getMaterial()
                .stream()
                .forEach(materialEntity -> materialEntity.setValidado(false));

        return cirurgiaEntity;
    }

    public Cirurgia atualizarCirurgia(CirurgiaEntity cirurgiaEntity) {
        return CirurgiaMapper.mapToCirurgia(cirurgiaRepository.save(cirurgiaEntity));
    }


}
