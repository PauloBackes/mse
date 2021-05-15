package com.unisinos.mse.controller;

@Controller
@AllArgsConstructor
public class MaterialController {
    CirurgiaService cirurgiaService;


    @RequestMapping(value = "/materiais", method = RequestMethod.GET)
    public ModelAndView telaExemplo4() {
        var cirurgia = cirurgiaService.buscarCirurgiaPeloId("6093064ec6a4f928997c356c");
        ModelAndView mv = new ModelAndView("materiais");
        mv.addObject("titulo", "Sai do meu colo, bicho!");
        mv.addObject("listaMateriais", cirurgia.getMaterial());
        return mv;
    }
}