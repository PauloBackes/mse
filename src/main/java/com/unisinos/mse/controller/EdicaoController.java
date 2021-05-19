package com.unisinos.mse.controller;

import com.unisinos.mse.service.CirurgiaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class EdicaoController {

    private CirurgiaService cirurgiaService;

    @RequestMapping(value = "/edicao", method = RequestMethod.GET)
    public ModelAndView telaEdicao() {
        var cirurgia = cirurgiaService.buscarCirurgiaPeloId("6093064ec6a4f928997c356c");
        ModelAndView mv = new ModelAndView("checklist");
        mv.addObject("titulo", "Sai do meu colo, bicho!");
        mv.addObject("listaMateriais", cirurgia.getMaterial());
        return mv;
    }

}
