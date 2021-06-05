package com.unisinos.mse.controller;

import com.unisinos.mse.facade.RelatorioFacade;
import com.unisinos.mse.model.Cirurgia;
import com.unisinos.mse.service.CirurgiaService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@AllArgsConstructor
@Log
public class CirurgiaController {


    CirurgiaService cirurgiaService;
    RelatorioFacade relatorioFacade;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView homePage() {

        var cirurgias = cirurgiaService.buscarTodasCirurgias();

        ModelAndView mv = new ModelAndView("cirurgias");
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
    public ModelAndView telaChecklist(@RequestParam(value = "id") String id) {
        var cirurgia = cirurgiaService.buscarCirurgiaPeloId(id);
        ModelAndView mv = new ModelAndView("checklist");
        mv.addObject("cirurgia", cirurgia);
        mv.addObject("equipamentos", cirurgia.getEquipamento());
        mv.addObject("materiais", cirurgia.getMaterial());
        return mv;
    }

    @RequestMapping(value = "/atualizar/cirurgia", method = RequestMethod.POST)
    public ModelAndView atualizarInstrumentosValidadosCirurgia(@ModelAttribute Cirurgia cirurgia,
                                                               @RequestParam(value = "equipamentosSelecionados", required = false) String[] equipamentosSelecionados,
                                                               @RequestParam(value = "materiaisSelecionados", required = false) String[] materiaisSelecionados,
                                                               @RequestParam(value = "idCirurgia") String idCirurgia,
                                                               RedirectAttributes redirectAttributes) {

        var cirurgiaAtualizada = cirurgiaService.atualizarInstrumentosValidados(idCirurgia, equipamentosSelecionados, materiaisSelecionados);
        redirectAttributes.addFlashAttribute("message", "Atualizado com sucesso");

        ModelAndView mv = new ModelAndView("redirect:/validar/cirurgia?id=" +
                cirurgiaAtualizada.getId());
        return mv;
    }

    @RequestMapping(value = "/relatorio/cirurgia", method = RequestMethod.GET)
    public void gerarRelatorio(@RequestParam(value = "id") String id,
                               HttpServletResponse response) {
        try {
            Path file = Paths.get(relatorioFacade.gerarRelatorioCirurgia(id).getAbsolutePath());
            if (Files.exists(file)) {
                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition",
                        "inline; filename=" + file.getFileName());
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
            log.info("Pdf enviado para a nova aba com sucesso");
        } catch (IOException ex) {
            log.info("Erro ao enviar o pdf criado para uma nova tela. Mensagem de erro: "+ex.getMessage());
            ex.printStackTrace();
        }
    }

}
