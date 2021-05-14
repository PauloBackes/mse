package com.unisinos.mse.controller;

import com.unisinos.mse.service.CirurgiaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class TesteController {


    CirurgiaService cirurgiaService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView homePage() {

        var cirurgias = cirurgiaService.buscarTodasCirurgias();

        ModelAndView mv = new ModelAndView("cirurgias");
        mv.addObject("titulo", "Sai do meu colo, bicho!");
        mv.addObject("listaCirurgias", cirurgias);
        return mv;
    }

    @RequestMapping(value = "/tela1", method = RequestMethod.GET)
    public String telaExemplo1() {
        return "tela1";
    }

    @RequestMapping(value = "/cirurgias", method = RequestMethod.GET)
    public ModelAndView telaExemplo2() {
        var cirurgias = cirurgiaService.buscarTodasCirurgias();
        ModelAndView mv = new ModelAndView("cirurgias");
        mv.addObject("titulo", "Sai do meu colo, bicho!");
        mv.addObject("listaCirurgias", cirurgias);
        return mv;
    }

    @RequestMapping(value = "/equipamentos", method = RequestMethod.GET)
    public ModelAndView telaExemplo3() {
        var cirurgia = cirurgiaService.buscarCirurgiaPeloId("6093064ec6a4f928997c356c");
        ModelAndView mv = new ModelAndView("equipamentos");
        mv.addObject("titulo", "Sai do meu colo, bicho!");
        mv.addObject("listaEquipamentos", cirurgia.getEquipamento());
        return mv;
    }

    @RequestMapping(value = "/materiais", method = RequestMethod.GET)
    public ModelAndView telaExemplo4() {
        var cirurgia = cirurgiaService.buscarCirurgiaPeloId("6093064ec6a4f928997c356c");
        ModelAndView mv = new ModelAndView("materiais");
        mv.addObject("titulo", "Sai do meu colo, bicho!");
        mv.addObject("listaMateriais", cirurgia.getMaterial());
        return mv;
    }
}
