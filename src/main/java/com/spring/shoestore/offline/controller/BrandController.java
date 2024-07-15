package com.spring.shoestore.offline.controller;

import com.spring.shoestore.offline.entity.Brand;
import com.spring.shoestore.offline.repository.BrandRepository;
import com.spring.shoestore.offline.service.BrandService;
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
@RequestMapping("/brands")
public class BrandController {
    private final BrandRepository brandRepository;
    private final BrandService brandService;

    public BrandController(BrandRepository brandRepository, BrandService brandService) {
        this.brandRepository = brandRepository;
        this.brandService = brandService;
    }

    @GetMapping("/")
    public String index(@ModelAttribute("brand")Brand brand, Model model,
                        @RequestParam(defaultValue = "1") int page ,
                        @RequestParam(defaultValue = "5") int size) {

        Page<Brand> brandPage = brandService.findPaginated(page,size);
        model.addAttribute("brands", brandPage.getContent());
        model.addAttribute("brandd", brandPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "/offline/brand/index";
    }

    @PostMapping("/create")
    public String create(Model model, @Validated @ModelAttribute("brand")Brand brand, BindingResult bindingResult,
                         @RequestParam(defaultValue = "1") int page ,
                         @RequestParam(defaultValue = "5") int size) {
        if (validate(model, bindingResult, page, size)) return "/offline/brand/index";
        brandRepository.save(brand);
        return "redirect:/brands/";
    }

    private boolean validate(Model model, BindingResult bindingResult, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        if (bindingResult.hasErrors()) {
            Page<Brand> brandPage = brandService.findPaginated(page,size);
            model.addAttribute("brands", brandPage.getContent());
            model.addAttribute("brandd", brandPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            return true;
        }
        return false;
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id,  @ModelAttribute("brand")Brand brand, Model model,
                       @RequestParam(defaultValue = "1") int page ,
                       @RequestParam(defaultValue = "5") int size) {
        brand = brandRepository.findById(id).get();
        model.addAttribute("brand", brand);
        Page<Brand> brandPage = brandService.findPaginated(page,size);
        model.addAttribute("brands", brandPage.getContent());
        model.addAttribute("brandd", brandPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "/offline/brand/index";

    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id,@Validated @ModelAttribute("brand")Brand brand, Model model, BindingResult bindingResult,
                         @RequestParam(defaultValue = "1") int page ,
                         @RequestParam(defaultValue = "5") int size) {
        if (validate(model, bindingResult, page, size)) return "/offline/brand/index";
        brand.setId(id);
        brandRepository.save(brand);
        return "redirect:/brands/";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        brandRepository.deleteById(id);
        return "redirect:/brands/";
    }

    @PostMapping("/filter")
    public String filter(@ModelAttribute("brand")Brand brand, Model model,
                         @RequestParam(defaultValue = "0") int page ,
                         @RequestParam(defaultValue = "5") int size,
                         @RequestParam(value = "brandCodeSearch",defaultValue = "") String brandCodeSearch,
                         @RequestParam(value = "brandNameSearch",defaultValue = "") String brandNameSearch) {
        Sort sort = Sort.by(Sort.Order.desc("_id_brand"));
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Brand> brandPage = brandRepository.search(brandCodeSearch,brandNameSearch,pageable);
        model.addAttribute("brands", brandPage.getContent());
        model.addAttribute("brandd", brandPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "/offline/brand/index";

    }

}
