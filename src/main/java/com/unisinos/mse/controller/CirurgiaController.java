package com.unisinos.mse.controller;

import com.unisinos.mse.facade.CirurgiaFacade;
import com.unisinos.mse.facade.RelatorioFacade;
import com.unisinos.mse.model.Cirurgia;
import com.unisinos.mse.model.Pesquisa;
import com.unisinos.mse.model.RemoverItem;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@Log
public class CirurgiaController {
    RelatorioFacade relatorioFacade;
    CirurgiaFacade cirurgiaFacade;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView homePage() {
        var cirurgias = cirurgiaFacade.buscarProximasCirurgias();

        ModelAndView mv = new ModelAndView("home");
        mv.addObject("titulo", "Sai do meu colo, bicho!");
        mv.addObject("listaCirurgias", cirurgias);
        mv.addObject("pesquisa", Pesquisa.builder().build());
        return mv;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView telaLogin() {
        ModelAndView mv = new ModelAndView("login");
        return mv;
    }

    @RequestMapping(value = "/cirurgias", method = RequestMethod.GET)
    public ModelAndView telaExemplo2() {
        var cirurgias = cirurgiaFacade.buscarTodasCirurgias();
        ModelAndView mv = new ModelAndView("cirurgias");
        mv.addObject("titulo", "Sai do meu colo, bicho!");
        mv.addObject("listaCirurgias", cirurgias);
        mv.addObject("pesquisa", Pesquisa.builder().build());
        return mv;
    }

    @RequestMapping(value = "/editar/cirurgia", method = RequestMethod.GET)
    public ModelAndView telaEdicaoCirurgia(@RequestParam(value = "id") Integer id,
                                           HttpSession session) {

        var cirurgia = cirurgiaFacade.buscarCirurgiaPeloId(id);
        session.setAttribute("cirurgia", cirurgia);
        ModelAndView mv = new ModelAndView("editar");
        mv.addObject("cirurgia", cirurgia);
        mv.addObject("equipamentos", cirurgia.getEquipamento());
        return mv;
    }

    @RequestMapping(value = "/validar/cirurgia", method = RequestMethod.GET)
    public ModelAndView telaChecklist(@RequestParam(value = "id") Integer id) {
        var cirurgia = cirurgiaFacade.buscarCirurgiaPeloId(id);
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

        var cirurgiaAtualizada = cirurgiaFacade.atualizarInstrumentosValidados(Integer.valueOf(idCirurgia), equipamentosSelecionados, materiaisSelecionados);
        redirectAttributes.addFlashAttribute("message", "Atualizado com sucesso");

        ModelAndView mv = new ModelAndView("redirect:/validar/cirurgia?id=" +
                cirurgiaAtualizada.getId());
        return mv;
    }

    @RequestMapping(value = "/relatorio/cirurgia", method = RequestMethod.GET)
    public void gerarRelatorio(@RequestParam(value = "id") Integer id,
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
            log.info("Erro ao enviar o pdf criado para uma nova tela. Mensagem de erro: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/pesquisar/cirurgia", method = RequestMethod.POST)
    public ModelAndView pesquisarCirurgia(@ModelAttribute Pesquisa pesquisa) {

        List<Cirurgia> cirurgias = null;
        if (ObjectUtils.isEmpty(pesquisa.getCampo())) {
            cirurgias = cirurgiaFacade.buscarTodasCirurgias();
        } else {
            var cirurgia = cirurgiaFacade.buscarCirurgiaPeloId(Integer.valueOf(pesquisa.getCampo()));
            if (!ObjectUtils.isEmpty(cirurgia)) {
                cirurgias = new ArrayList<>();
                cirurgias.add(cirurgia);
            }

        }

        ModelAndView mv = new ModelAndView("cirurgias");
        mv.addObject("listaCirurgias", cirurgias);
        mv.addObject("pesquisa", pesquisa);
        return mv;
    }

    //TODO trocar para atualizar/cirurgia
    @RequestMapping(value = "/atualizar/edicao", method = RequestMethod.POST)
    public ModelAndView atualizarCirurgia(@ModelAttribute Cirurgia cirurgia,
                                          RedirectAttributes redirectAttributes) {

        var cirurgiaAtualizada = cirurgiaFacade.atualizarCirurgia(cirurgia);
        redirectAttributes.addFlashAttribute("message", "Atualizado com sucesso");
        ModelAndView mv = new ModelAndView("redirect:/editar/cirurgia?id=" +
                cirurgiaAtualizada.getId());
        return mv;
    }

    @RequestMapping(value = "/remover/item", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    Cirurgia removerItemDaCirurgia(@ModelAttribute RemoverItem removerItem) {
        return cirurgiaFacade.removerItem(removerItem);
    }
}
