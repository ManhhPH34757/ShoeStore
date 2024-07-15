package com.spring.shoestore.offline.controller;


import com.spring.shoestore.offline.entity.Category;
import com.spring.shoestore.offline.repository.CategoryRepository;
import com.spring.shoestore.offline.service.CategoryService;
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
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    public CategoryController(CategoryRepository categoryRepository, CategoryService categoryService) {
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String index(@ModelAttribute("category")Category category, Model model,
                        @RequestParam(defaultValue = "1") int page ,
                        @RequestParam(defaultValue = "5") int size){
        Page<Category> categoryPage = categoryService.findPaginated(page,size);
        model.addAttribute("listCategory",categoryPage.getContent());
        model.addAttribute("categori",categoryPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "/offline/category/index";
    }

    @PostMapping("/create")
    public String create(Model model, @Validated @ModelAttribute("category") Category category, BindingResult bindingResult,
                         @RequestParam(defaultValue = "1") int page ,
                         @RequestParam(defaultValue = "5") int size) {
        if (validate(model, bindingResult, page, size)) return "/offline/category/index";
        categoryRepository.save(category);
        return "redirect:/categories/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id,  @ModelAttribute("category") Category category,
                       Model model, BindingResult bindingResult,
                       @RequestParam(defaultValue = "1") int page ,
                       @RequestParam(defaultValue = "5") int size) {
        category = categoryRepository.findById(id).get();
        model.addAttribute("category", category);
        Page<Category> categoryPage = categoryService.findPaginated(page,size);
        model.addAttribute("listCategory",categoryPage.getContent());
        model.addAttribute("categori",categoryPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "/offline/category/index";

    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id,@Validated @ModelAttribute("category") Category category,
                         Model model, BindingResult bindingResult,
                         @RequestParam(defaultValue = "1") int page ,
                         @RequestParam(defaultValue = "5") int size) {
        if (validate(model, bindingResult, page, size)) return "/offline/category/index";
        category.setId(id);
        categoryRepository.save(category);
        return "redirect:/categories/";
    }

    private boolean validate(Model model, BindingResult bindingResult, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        if (bindingResult.hasErrors()){
            Page<Category> categoryPage = categoryService.findPaginated(page,size);
            model.addAttribute("listCategory",categoryPage.getContent());
            model.addAttribute("categori",categoryPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            return true;
        }
        return false;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        categoryRepository.deleteById(id);
        return "redirect:/categories/";
    }

    @PostMapping("/filter")
    public String filter(@ModelAttribute("category") Category category, Model model,
                         @RequestParam(defaultValue = "0") int page ,
                         @RequestParam(defaultValue = "5") int size,
                         @RequestParam(value = "categoryCodeSearch",defaultValue = "") String categoryCodeSearch,
                         @RequestParam(value = "categoryNameSearch",defaultValue = "") String categoryNameSearch) {
        Sort sort = Sort.by(Sort.Order.desc("_id_category"));
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Category> categoryPage = categoryRepository.search(categoryCodeSearch,categoryNameSearch,pageable);
        model.addAttribute("listCategory",categoryPage.getContent());
        model.addAttribute("categori",categoryPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "/offline/category/index";

    }


}
