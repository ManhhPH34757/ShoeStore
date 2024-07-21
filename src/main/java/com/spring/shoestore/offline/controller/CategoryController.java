package com.spring.shoestore.offline.controller;

import com.spring.shoestore.offline.entity.Brand;
import com.spring.shoestore.offline.entity.Category;
import com.spring.shoestore.offline.repository.CategoryRepository;
import com.spring.shoestore.offline.services.CategoryService;
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
@RequestMapping("/categorys")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/")
    public String getCategorys(Model model, @ModelAttribute("category") Category category, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size) {
        Page<Category> productPage = categoryService.findPaginated(page, size);
        model.addAttribute("listCategory", productPage.getContent());
        model.addAttribute("listCategorys", productPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "offline/category/index";
    }

    @GetMapping("/{id}")
    public String getCategory(Model model, @ModelAttribute("category") Category category, @PathVariable("id") Integer id, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size) {
        category = categoryRepository.findById(id).get();
        model.addAttribute("category", category);
        Page<Category> categoryPage = categoryService.findPaginated(page, size);
        model.addAttribute("listCategory", categoryPage.getContent());
        model.addAttribute("listCategorys", categoryPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "offline/category/index";
    }

    @PostMapping("/update/{id}")
    public String update(Model model, @ModelAttribute("category") Category category, @PathVariable("id") Integer id
            , @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Page<Category> productPage = categoryService.findPaginated(page, size);
            model.addAttribute("listCategory", productPage.getContent());
            model.addAttribute("listCategorys", productPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            return "offline/category/index";
        }
        category.setId(id);
        categoryRepository.save(category);
        return "redirect:/categorys/";
    }

    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("category") Category category, BindingResult bindingResult,Model model,
                         @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size) {
        if (bindingResult.hasErrors()) {
            Page<Category> productPage = categoryService.findPaginated(page, size);
            model.addAttribute("listCategory", productPage.getContent());
            model.addAttribute("listCategorys", productPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            return "offline/category/index";
        }
        categoryRepository.save(category);
        return "redirect:/categorys/";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Integer id) {
        categoryRepository.deleteById(id);
        return "redirect:/categorys/";
    }

    @PostMapping("/filter")
    public String filter(Model model, @ModelAttribute("category") Category category, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size,
                         @RequestParam(value = "categoryCodeSearch") String categoryCodeSearch, @RequestParam(value = "categoryNameSearch") String categoryNameSearch) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryRepository.search(categoryCodeSearch, categoryNameSearch, pageable);
        model.addAttribute("listCategory", categoryPage.getContent());
        model.addAttribute("listCategorys", categoryPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "offline/category/index";
    }

}
