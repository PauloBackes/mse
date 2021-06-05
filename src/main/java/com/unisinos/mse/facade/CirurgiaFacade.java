package com.unisinos.mse.facade;

import com.unisinos.mse.model.Cirurgia;
import com.unisinos.mse.service.CirurgiaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CirurgiaFacade {
    CirurgiaService cirurgiaService;

    public Cirurgia buscarCirurgiaPeloId(Integer id) {
        return cirurgiaService.buscarCirurgiaPeloId(id);
    }

    public List<Cirurgia> buscarTodasCirurgias() {
        return cirurgiaService.buscarTodasCirurgias();
    }

    public Cirurgia atualizarInstrumentosValidados(Integer cirurgiaId,
                                                   String[] equipamentosSelecionados,
                                                   String[] materiaisSelecionados) {

        return cirurgiaService.atualizarInstrumentosValidados(cirurgiaId,
                equipamentosSelecionados,
                materiaisSelecionados);
    }
}
