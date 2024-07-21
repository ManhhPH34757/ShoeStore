package com.spring.shoestore.offline.controller;

import com.spring.shoestore.offline.entity.Brand;
import com.spring.shoestore.offline.entity.Category;
import com.spring.shoestore.offline.entity.Material;
import com.spring.shoestore.offline.repository.MaterialRepository;
import com.spring.shoestore.offline.services.MaterialService;
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
@RequestMapping("/materials")
public class MaterialController {
    @Autowired
    MaterialRepository materialRepository;
    @Autowired
    MaterialService materialService;

    @GetMapping("/")
    public String getMaterials(@ModelAttribute("material") Material material, Model model, @RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "3")int size) {
        Page<Material> productPage = materialService.findPaginated(page,size);
        model.addAttribute("listMaterial", productPage.getContent());
        model.addAttribute("listMaterials", productPage);
        model.addAttribute("currentPage",page);
        model.addAttribute("pageSize",size);
        return "offline/material/index";
    }
    @GetMapping("/{id}")
    public String getBrands(Model model, @ModelAttribute("material") Material material, @PathVariable("id") Integer id, @RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "3")int size) {
        material = materialRepository.findById(id).get();
        model.addAttribute("material", material);
        Page<Material> materialPage = materialService.findPaginated(page,size);
        model.addAttribute("listMaterial", materialPage.getContent());
        model.addAttribute("listMaterials", materialPage);
        model.addAttribute("currentPage",page);
        model.addAttribute("pageSize",size);
        return "offline/material/index";
    }
    @PostMapping("/update/{id}")
    public String update(Model model,@ModelAttribute("material") Material material,@PathVariable("id") Integer id
            ,@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "3")int size,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Page<Material> materialPage = materialService.findPaginated(page,size);
            model.addAttribute("listMaterial", materialPage.getContent());
            model.addAttribute("listMaterials", materialPage);
            model.addAttribute("currentPage",page);
            model.addAttribute("pageSize",size);
            return "offline/material/index";
        }
        material.setId(id);
        materialRepository.save(material);
        return "redirect:/materials/";
    }
    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("material") Material material, BindingResult bindingResult, Model model,
                         @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size) {
        if (bindingResult.hasErrors()) {
            Page<Material> productPage = materialService.findPaginated(page, size);
            model.addAttribute("listMaterial", productPage.getContent());
            model.addAttribute("listMaterials", productPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            return "offline/material/index";
        }
        materialRepository.save(material);
        return "redirect:/materials/";
    }
    @GetMapping("/delete/{id}")
    public String deleteBrand(@PathVariable("id") Integer id) {
        materialRepository.deleteById(id);
        return "redirect:/materials/";
    }
    @PostMapping("/filter")
    public String filter(@ModelAttribute("material") Material material, Model model, @RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "3")int size,
                         @RequestParam(value = "materialCodeSearch")String materialCodeSearch,@RequestParam(value = "materialNameSearch")String materialNameSearch ) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Material> materialPage = materialRepository.search(materialCodeSearch,materialNameSearch,pageable);
        model.addAttribute("listMaterial", materialPage.getContent());
        model.addAttribute("listMaterials",materialPage);
        model.addAttribute("currentPage",page);
        model.addAttribute("pageSize",size);
        return "offline/material/index";
    }
}
