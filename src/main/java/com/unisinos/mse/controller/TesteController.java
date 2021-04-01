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
        mv.addObject("titulo", "Tá pegando fogo, bicho!");
        return mv;
    }
}
