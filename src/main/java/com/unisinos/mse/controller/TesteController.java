package com.unisinos.mse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TesteController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView homePage() {
        ModelAndView mv = new ModelAndView("home");
        mv.addObject("titulo", "Sai do meu colo, bicho!");
        return mv;
    }

    @RequestMapping(value = "/tela1", method = RequestMethod.GET)
    public String telaExemplo1() {
        return "tela1";
    }

    @RequestMapping(value = "/cirurgias", method = RequestMethod.GET)
    public String telaExemplo2() {
        return "cirurgias";
    }

    @RequestMapping(value = "/equipamentos", method = RequestMethod.GET)
    public String telaExemplo3() {
        return "equipamentos";
    }

    @RequestMapping(value = "/materiais", method = RequestMethod.GET)
    public String telaExemplo4() {
        return "materiais";
    }
}
