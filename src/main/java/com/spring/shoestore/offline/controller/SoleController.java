package com.spring.shoestore.offline.controller;

import com.spring.shoestore.offline.entity.Material;
import com.spring.shoestore.offline.entity.Sole;
import com.spring.shoestore.offline.repository.SoleRepository;
import com.spring.shoestore.offline.service.SoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/soles")
public class SoleController {
    private final SoleRepository soleRepository;
    private final SoleService soleService;


    public SoleController(SoleRepository soleRepository, SoleService soleService) {
        this.soleRepository = soleRepository;
        this.soleService = soleService;
    }

    @GetMapping("/")
    public String index(@ModelAttribute("sole")Sole sole, Model model,
                        @RequestParam(defaultValue = "1") int page ,
                        @RequestParam(defaultValue = "5") int size){
        Page<Sole> solePage = soleService.findPaginated(page,size);
        model.addAttribute("listSole",solePage.getContent());
        model.addAttribute("solee",solePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "/offline/sole/index";
    }

    @PostMapping("/create")
    public String create(Model model, @Validated @ModelAttribute("sole")Sole sole, BindingResult bindingResult,
                         @RequestParam(defaultValue = "1") int page ,
                         @RequestParam(defaultValue = "5") int size) {
        if (bindingResult.hasErrors()){
            Page<Sole> solePage = soleService.findPaginated(page,size);
            model.addAttribute("listSole",solePage.getContent());
            model.addAttribute("solee",solePage);
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            return "/offline/sole/index";
        }
        soleRepository.save(sole);
        return "redirect:/soles/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id,  @ModelAttribute("sole")Sole sole,
                       Model model, BindingResult bindingResult,
                       @RequestParam(defaultValue = "1") int page ,
                       @RequestParam(defaultValue = "5") int size) {
        sole = soleRepository.findById(id).get();
        model.addAttribute("sole", sole);
        Page<Sole> solePage = soleService.findPaginated(page,size);
        model.addAttribute("listSole",solePage.getContent());
        model.addAttribute("solee",solePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "/offline/sole/index";

    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id,@Validated @ModelAttribute("sole")Sole sole,
                         Model model, BindingResult bindingResult,
                         @RequestParam(defaultValue = "1") int page ,
                         @RequestParam(defaultValue = "5") int size) {
        if (bindingResult.hasErrors()){
            Page<Sole> solePage = soleService.findPaginated(page,size);
            model.addAttribute("listSole",solePage.getContent());
            model.addAttribute("solee",solePage);
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            return "/offline/sole/index";
        }
        sole.setId(id);
        soleRepository.save(sole);
        return "redirect:/soles/";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        soleRepository.deleteById(id);
        return "redirect:/soles/";
    }

    @PostMapping("/filter")
    public String filter(@ModelAttribute("sole") Sole sole, Model model,
                         @RequestParam(defaultValue = "0") int page ,
                         @RequestParam(defaultValue = "5") int size,
                         @RequestParam(value = "soleCodeSearch",defaultValue = "") String soleCodeSearch,
                         @RequestParam(value = "soleNameSearch",defaultValue = "") String soleNameSearch) {
        Sort sort = Sort.by(Sort.Order.desc("_id_sole"));
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Sole> solePage = soleRepository.search(soleCodeSearch,soleNameSearch,pageable);
        model.addAttribute("listSole",solePage.getContent());
        model.addAttribute("solee",solePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "/offline/sole/index";

    }

}
