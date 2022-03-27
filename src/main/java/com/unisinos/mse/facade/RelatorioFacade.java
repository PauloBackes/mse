package com.unisinos.mse.facade;

import com.unisinos.mse.service.RelatorioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@AllArgsConstructor
public class RelatorioFacade {

    private RelatorioService relatorioService;
    private CirurgiaFacade cirurgiaFacade;

    /*public File gerarRelatorioCirurgia(Integer idCirurgia) {
        var cirurgia = cirurgiaFacade.buscarCirurgiaPeloId(idCirurgia);
        return relatorioService.gerarRelatorioCirurgia(cirurgia);
    }*/
}
