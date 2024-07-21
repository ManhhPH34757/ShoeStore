package com.spring.shoestore.offline.controller;

import com.spring.shoestore.offline.entity.Brand;
import com.spring.shoestore.offline.entity.Category;
import com.spring.shoestore.offline.entity.Material;
import com.spring.shoestore.offline.entity.Sole;
import com.spring.shoestore.offline.repository.SoleRepository;
import com.spring.shoestore.offline.services.SoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/soles")
public class SoleController {
    @Autowired
    SoleRepository soleRepository;
    @Autowired
    SoleService soleService;

    @GetMapping("/")
    public String getSoles(@ModelAttribute("sole") Sole sole, Model model, @RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "3")int size) {
        Page<Sole> productPage = soleService.findPaginated(page,size);
        model.addAttribute("listSole", productPage.getContent());
        model.addAttribute("listSoles", productPage);
        model.addAttribute("currentPage",page);
        model.addAttribute("pageSize",size);
        return "offline/sole/index";
    }
    @GetMapping("/{id}")
    public String getBrands(Model model, @ModelAttribute("sole") Sole sole, @PathVariable("id") Integer id, @RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "3")int size) {
        sole = soleRepository.findById(id).get();
        model.addAttribute("sole", sole);
        Page<Sole> solePage = soleService.findPaginated(page,size);
        model.addAttribute("listSole", solePage.getContent());
        model.addAttribute("listSoles", solePage);
        model.addAttribute("currentPage",page);
        model.addAttribute("pageSize",size);
        return "offline/sole/index";
    }
    @PostMapping("/update/{id}")
    public String update(Model model,@ModelAttribute("sole") Sole sole,@PathVariable("id") Integer id
            ,@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "3")int size,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Page<Sole> solePage = soleService.findPaginated(page,size);
            model.addAttribute("listSole", solePage.getContent());
            model.addAttribute("listSoles", solePage);
            model.addAttribute("currentPage",page);
            model.addAttribute("pageSize",size);
            return "offline/sole/index";
        }
        sole.setId(id);
        soleRepository.save(sole);
        return "redirect:/soles/";
    }
    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("sole") Sole sole, BindingResult bindingResult, Model model,
                         @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size) {
        if (bindingResult.hasErrors()) {
            Page<Sole> solePage = soleService.findPaginated(page, size);
            model.addAttribute("listSole", solePage.getContent());
            model.addAttribute("listSoles", solePage);
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            return "offline/sole/index";
        }
        soleRepository.save(sole);
        return "redirect:/soles/";
    }
    @GetMapping("/delete/{id}")
    public String deleteBrand(@PathVariable("id") Integer id) {
       soleRepository.deleteById(id);
        return "redirect:/soles/";
    }
    @PostMapping("/filter")
    public String filter(@ModelAttribute("sole") Sole sole, Model model, @RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "3")int size,
                         @RequestParam(value = "soleCodeSearch")String soleCodeSearch,@RequestParam(value = "soleNameSearch")String soleNameSearch ) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Sole> solePage = soleRepository.search(soleCodeSearch,soleNameSearch,pageable);
        model.addAttribute("listSole", solePage.getContent());
        model.addAttribute("listSoles",solePage);
        model.addAttribute("currentPage",page);
        model.addAttribute("pageSize",size);
        return "offline/sole/index";
    }
}
