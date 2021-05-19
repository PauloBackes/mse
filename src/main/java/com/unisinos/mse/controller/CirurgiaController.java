package com.unisinos.mse.controller;

import com.unisinos.mse.model.Cirurgia;
import com.unisinos.mse.service.CirurgiaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class CirurgiaController {


    CirurgiaService cirurgiaService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView homePage() {

        var cirurgias = cirurgiaService.buscarTodasCirurgias();

        ModelAndView mv = new ModelAndView("home");
        mv.addObject("titulo", "Sai do meu colo, bicho!");
        mv.addObject("listaCirurgias", cirurgias);
        return mv;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView telaLogin() {
        ModelAndView mv = new ModelAndView("login");
        return mv;
    }

    @RequestMapping(value = "/cirurgias", method = RequestMethod.GET)
    public ModelAndView telaExemplo2() {
        var cirurgias = cirurgiaService.buscarTodasCirurgias();
        ModelAndView mv = new ModelAndView("cirurgias");
        mv.addObject("titulo", "Sai do meu colo, bicho!");
        mv.addObject("listaCirurgias", cirurgias);
        return mv;
    }

    @RequestMapping(value = "/editar/cirurgia", method = RequestMethod.GET)
    public ModelAndView telaEdicaoCirurgia() {
        ModelAndView mv = new ModelAndView("editarCirurgia");
        return mv;
    }

    @RequestMapping(value = "/validar/cirurgia", method = RequestMethod.GET)
    public ModelAndView telaChecklist(String id) {
        var cirurgia = cirurgiaService.buscarCirurgiaPeloId(id);
        ModelAndView mv = new ModelAndView("checklist");
        mv.addObject("cirurgia", cirurgia);
        return mv;
    }

    @RequestMapping(value = "/atualizar/cirurgia", method = RequestMethod.POST)
    public void atualizacaoCirurgia(@ModelAttribute Cirurgia cirurgia, Model model ) {
        Integer a = 90;
    }

  }
