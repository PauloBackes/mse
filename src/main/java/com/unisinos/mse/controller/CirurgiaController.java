package com.unisinos.mse.controller;

import com.unisinos.mse.model.Cirurgia;
import com.unisinos.mse.service.CirurgiaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
        cirurgia.setCheckbox(new String[] {"Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"});
        ModelAndView mv = new ModelAndView("checklist");
        mv.addObject("cirurgia", cirurgia);
        mv.addObject("equipamentos", cirurgia.getEquipamento());
        mv.addObject("materiais", cirurgia.getMaterial());
        return mv;
    }

    @RequestMapping(value = "/atualizar/cirurgia", method = RequestMethod.POST)
    public ModelAndView atualizarInstrumentosValidadosCirurgia(@ModelAttribute Cirurgia cirurgia,
                                                       @RequestParam(value = "equipamentosSelecionados" , required = false) String[] equipamentosSelecionados,
                                                       @RequestParam(value = "materiaisSelecionados" , required = false) String[] materiaisSelecionados,
                                                       @RequestParam(value = "idCirurgia") String idCirurgia) {

        var cirurgiaAtualizada = cirurgiaService.atualizarInstrumentosValidados(idCirurgia, equipamentosSelecionados, materiaisSelecionados);
        ModelAndView mv = new ModelAndView("checklist");
        mv.addObject("cirurgia", cirurgiaAtualizada);
        mv.addObject("equipamentos", cirurgiaAtualizada.getEquipamento());
        mv.addObject("materiais", cirurgiaAtualizada.getMaterial());
        return mv;
    }

  }
