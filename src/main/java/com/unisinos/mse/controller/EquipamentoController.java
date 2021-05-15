package com.unisinos.mse.controller;

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