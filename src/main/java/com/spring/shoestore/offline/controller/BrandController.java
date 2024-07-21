package com.spring.shoestore.offline.controller;

import com.spring.shoestore.offline.entity.Brand;
import com.spring.shoestore.offline.entity.Category;
import com.spring.shoestore.offline.repository.BrandRepository;
import com.spring.shoestore.offline.services.BrandService;
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
@RequestMapping("/brands")
public class BrandController {
    @Autowired
    BrandService brandService;
    @Autowired
    BrandRepository brandRepository;

    @GetMapping("/")
    public String getBrands(Model model,@ModelAttribute("brand") Brand brand, @RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "3")int size) {
        Page<Brand> productPage = brandService.findPaginated(page,size);
        model.addAttribute("listBrand", productPage.getContent());
        model.addAttribute("listBrands", productPage);
        model.addAttribute("currentPage",page);
        model.addAttribute("pageSize",size);
        return "offline/brand/index";
    }
    @GetMapping("/{id}")
    public String getBrands(Model model, @ModelAttribute("brand") Brand brand, @PathVariable("id") Integer id, @RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "3")int size) {
        brand = brandRepository.findById(id).get();
        model.addAttribute("brand", brand);
        Page<Brand> brandPage = brandService.findPaginated(page,size);
        model.addAttribute("listBrand", brandPage.getContent());
        model.addAttribute("listBrands", brandPage);
        model.addAttribute("currentPage",page);
        model.addAttribute("pageSize",size);
        return "offline/brand/index";
    }
    @PostMapping("/update/{id}")
    public String update(Model model,@ModelAttribute("brand") Brand brand,@PathVariable("id") Integer id
            ,@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "3")int size,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Page<Brand> productPage = brandService.findPaginated(page,size);
            model.addAttribute("listBrand", productPage.getContent());
            model.addAttribute("listBrands", productPage);
            model.addAttribute("currentPage",page);
            model.addAttribute("pageSize",size);
            return "offline/brand/index";
        }
        brand.setId(id);
        brandRepository.save(brand);
        return "redirect:/brands/";
    }
    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("brand") Brand brand, BindingResult bindingResult, Model model,
                         @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size) {
        if (bindingResult.hasErrors()) {
            Page<Brand> productPage = brandService.findPaginated(page, size);
            model.addAttribute("listBrand", productPage.getContent());
            model.addAttribute("listBrands", productPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            return "offline/brand/index";
        }
        brandRepository.save(brand);
        return "redirect:/brands/";
    }
    @GetMapping("/delete/{id}")
    public String deleteBrand(@PathVariable("id") Integer id) {
        brandRepository.deleteById(id);
        return "redirect:/brands/";
    }
    @PostMapping("/filter")
    public String filter(@ModelAttribute("brand") Brand brand, Model model,@RequestParam(value = "brandCodeSearch")String brandCodeSearch,
                         @RequestParam(value = "brandNameSearch")String brandNameSearch,@RequestParam(defaultValue = "0")int page , @RequestParam(defaultValue = "3")int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Brand> brandPage = brandRepository.search(brandCodeSearch,brandNameSearch,pageable);
        model.addAttribute("listBrand", brandPage.getContent());
        model.addAttribute("listBrands",brandPage);
        model.addAttribute("currentPage",page);
        model.addAttribute("pageSize",size);
        return "offline/brand/index";
    }
}
