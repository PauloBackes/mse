package com.unisinos.mse.controller;

import com.unisinos.mse.repository.CirurgiaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class TesteController {

    CirurgiaRepository cirurgiaRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView homePage() {
        try {
            Integer a = 12;
            var t = cirurgiaRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ModelAndView mv = new ModelAndView("cirurgias");
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
