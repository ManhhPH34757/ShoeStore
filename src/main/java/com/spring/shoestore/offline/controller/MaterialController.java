package com.spring.shoestore.offline.controller;


import com.spring.shoestore.offline.entity.Material;
import com.spring.shoestore.offline.repository.MaterialRepository;
import com.spring.shoestore.offline.service.MaterialService;
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
@RequestMapping("/materials")
public class MaterialController {

    private final MaterialRepository materialRepository;
    private final MaterialService materialService;

    public MaterialController(MaterialRepository materialRepository, MaterialService materialService) {
        this.materialRepository = materialRepository;
        this.materialService = materialService;
    }

    @GetMapping("/")
    public String index(@ModelAttribute("material")Material material, Model model,
                        @RequestParam(defaultValue = "1") int page ,
                        @RequestParam(defaultValue = "5") int size){
        Page<Material> materialPage = materialService.findPaginated(page,size);
        model.addAttribute("listMaterial",materialPage.getContent());
        model.addAttribute("materiall",materialPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "/offline/material/index";
    }

    @PostMapping("/create")
    public String create(Model model, @Validated @ModelAttribute("material") Material material, BindingResult bindingResult,
                         @RequestParam(defaultValue = "1") int page ,
                         @RequestParam(defaultValue = "5") int size) {
        if (validate(model, bindingResult, page, size)) return "/offline/material/index";
        materialRepository.save(material);
        return "redirect:/materials/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id,  @ModelAttribute("material") Material material,
                       Model model, BindingResult bindingResult,
                       @RequestParam(defaultValue = "1") int page ,
                       @RequestParam(defaultValue = "5") int size) {
        material = materialRepository.findById(id).get();
        model.addAttribute("material", material);
        Page<Material> materialPage = materialService.findPaginated(page,size);
        model.addAttribute("listMaterial",materialPage.getContent());
        model.addAttribute("materiall",materialPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "/offline/material/index";

    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id,@Validated @ModelAttribute("material") Material material,
                         Model model, BindingResult bindingResult,
                         @RequestParam(defaultValue = "1") int page ,
                         @RequestParam(defaultValue = "5") int size) {
        if (validate(model, bindingResult, page, size)) return "/offline/material/index";
        material.setId(id);
        materialRepository.save(material);
        return "redirect:/materials/";
    }

    private boolean validate(Model model, BindingResult bindingResult, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        if (bindingResult.hasErrors()){
            Page<Material> materialPage = materialService.findPaginated(page,size);
            model.addAttribute("listMaterial",materialPage.getContent());
            model.addAttribute("materiall",materialPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            return true;
        }
        return false;
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        materialRepository.deleteById(id);
        return "redirect:/materials/";
    }

    @PostMapping("/filter")
    public String filter(@ModelAttribute("material") Material material, Model model,
                         @RequestParam(defaultValue = "0") int page ,
                         @RequestParam(defaultValue = "5") int size,
                         @RequestParam(value = "materialCodeSearch",defaultValue = "") String materialCodeSearch,
                         @RequestParam(value = "materialNameSearch",defaultValue = "") String materialNameSearch) {
        Sort sort = Sort.by(Sort.Order.desc("_id_material"));
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Material> materialPage = materialRepository.search(materialCodeSearch,materialNameSearch,pageable);
        model.addAttribute("listMaterial",materialPage.getContent());
        model.addAttribute("materiall",materialPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "/offline/material/index";

    }


}
