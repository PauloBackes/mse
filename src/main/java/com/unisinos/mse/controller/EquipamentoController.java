package com.unisinos.mse.controller;

import com.unisinos.mse.service.CirurgiaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class EquipamentoController {
    CirurgiaService cirurgiaService;

    @RequestMapping(value = "/equipamentos", method = RequestMethod.GET)
    public ModelAndView telaEquipamento() {
        var cirurgia = cirurgiaService.buscarCirurgiaPeloId("6093064ec6a4f928997c356c");
        ModelAndView mv = new ModelAndView("equipamentos");
        mv.addObject("listaEquipamentos", cirurgia.getEquipamento());
        return mv;
    }
}