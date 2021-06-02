package com.unisinos.mse.facade;

import com.unisinos.mse.model.Cirurgia;
import com.unisinos.mse.service.CirurgiaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CirurgiaFacade {
    CirurgiaService cirurgiaService;

    public Cirurgia buscarCirurgiaPeloId(String id) {
        return cirurgiaService.buscarCirurgiaPeloId(id);
    }
    public Cirurgia buscarCirurgiaPorDescricao(String descricao) {
        return buscarCirurgiaPorDescricao(descricao);
    }
}
