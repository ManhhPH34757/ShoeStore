package com.spring.shoestore.controllers.offline;


import com.spring.shoestore.entity.*;
import com.spring.shoestore.repo.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final BrandRepository brandRepository;

    private final CategoryRepository categoryRepository;

    private final MaterialRepository materialRepository;

    private final SoleRepository soleRepository;

    private final ProductRepository productRepository;

    public ProductController(BrandRepository brandRepository, CategoryRepository categoryRepository, MaterialRepository materialRepository, SoleRepository soleRepository, ProductRepository productRepository) {
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.materialRepository = materialRepository;
        this.soleRepository = soleRepository;
        this.productRepository = productRepository;
    }

    @ModelAttribute("brands")
    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @ModelAttribute("soles")
    public List<Sole> getSole() {
        return soleRepository.findAll();
    }

    @ModelAttribute("materials")
    public List<Material> getMaterials() {
        return materialRepository.findAll();
    }

    @GetMapping("/")
    public String getProducts(@ModelAttribute("product") Product product, Model model,
                              @RequestParam(defaultValue = "0") Integer pageNumber,
                              @RequestParam(defaultValue = "5") Integer pageSize

    ) {
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        model.addAttribute("listProduct", productRepository.findAll(pageable).getContent());

        model.addAttribute("pageNumber", productRepository.findAll(pageable).getNumber());
        model.addAttribute("pageSize", productRepository.findAll(pageable).getSize());
        return "offline/products/listProduct";
    }

    @PostMapping("/create")
    public String createProduct( Product product) {
        product.setDateCreated(Instant.now());
        productRepository.save(product);
        return "redirect:/products/";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editProduct(@PathVariable Integer id, @ModelAttribute("product") Product product, Model model) {
        model.addAttribute("listProduct", productRepository.findAll());
        Product product1 = productRepository.findById(id).get();
        return new ModelAndView("offline/products/listProduct", "product", product1);
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productRepository.deleteById(id);
        return "redirect:/products/";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Integer id, Product product) {
            product.setDateUpdated(Instant.now());
            product.setIdBrand(brandRepository.findById(product.getIdBrand().getId()).get());
            product.setIdCategory(categoryRepository.findById(product.getIdCategory().getId()).get());
            product.setIdMaterial(materialRepository.findById(product.getIdMaterial().getId()).get());
            product.setIdSole(soleRepository.findById(product.getIdSole().getId()).get());
            product.setId(id);
            productRepository.save(product);
            return "redirect:/products/";
    }



}
