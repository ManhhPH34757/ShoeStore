package com.spring.shoestore.offline.controller;

import com.spring.shoestore.offline.dto.ProductDto;
import com.spring.shoestore.offline.entity.*;
import com.spring.shoestore.offline.repository.*;
import com.spring.shoestore.offline.service.ProductDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

    public ProductController(BrandRepository brandRepository, CategoryRepository categoryRepository, MaterialRepository materialRepository, SoleRepository soleRepository, ProductRepository productRepository, ProductDtoRepository productDtoRepository) {
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.materialRepository = materialRepository;
        this.soleRepository = soleRepository;
        this.productRepository = productRepository;
        this.productDtoRepository = productDtoRepository;
    }

    private final ProductDtoRepository productDtoRepository;
    @Autowired
    private ProductDtoService productDtoService;


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
                              @RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "5")int size) {
        //model.addAttribute("listProduct", productDtoRepository.findAllQuantity());
        Page<ProductDto> productPage = productDtoService.findPaginated(page,size);
        model.addAttribute("listProduct",productPage.getContent());
        model.addAttribute("listProducts",productPage);
        model.addAttribute("currentPage",page);
        model.addAttribute("pageSize",size);
        return "/offline/products/index";
    }


    @GetMapping("/{id}")
    public String getProduct(@PathVariable Integer id,@ModelAttribute("product") Product product, Model model,
                             @RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "5")int size) {
        product = productRepository.findById(id).get();
        model.addAttribute("product",product);
        Page<ProductDto> productPage = productDtoService.findPaginated(page,size);
        model.addAttribute("listProduct",productPage.getContent());
        model.addAttribute("listProducts",productPage);
        model.addAttribute("currentPage",page);
        model.addAttribute("pageSize",size);
        return "/offline/products/index";
    }

    @PostMapping("/create")
    public String createProduct(@Validated @ModelAttribute("product") Product product,BindingResult bindingResult,Model model,
                                @RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "5")int size ) {
        if (bindingResult.hasErrors()) {
            Page<ProductDto> productPage = productDtoService.findPaginated(page,size);
            model.addAttribute("listProduct",productPage.getContent());
            model.addAttribute("listProducts",productPage);
            model.addAttribute("currentPage",page);
            model.addAttribute("pageSize",size);
            return "/offline/products/index";
        }
        product.setDateCreated(Instant.now());
        product.setDateUpdated(Instant.now());
        productRepository.save(product);
        return "redirect:/products/";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Integer id, @Validated @ModelAttribute("product") Product product, BindingResult bindingResult,Model model,
                                @RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "5")int size) {
        if (bindingResult.hasErrors()) {
            Page<ProductDto> productPage = productDtoService.findPaginated(page,size);
            model.addAttribute("listProduct",productPage.getContent());
            model.addAttribute("listProducts",productPage);
            model.addAttribute("currentPage",page);
            model.addAttribute("pageSize",size);
            return "/offline/products/index";
        }
        product.setId(id);
        product.setDateUpdated(Instant.now());
        productRepository.save(product);
        return "redirect:/products/";
    }

    @PostMapping("/filter")
    public String search(@ModelAttribute("product") Product product,Model model, @RequestParam("productNameSearch") String product_name, @RequestParam(value = "brandNameSearch",defaultValue = "") Integer brand_id,
                               @RequestParam( value = "categoryNameSearch", defaultValue = "") Integer category_id, @RequestParam(value = "materialNameSearch",defaultValue = "") Integer material_id,
                               @RequestParam(value = "soleNameSearch",defaultValue = "") Integer sole_id,
                         @RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "5")int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<ProductDto> productSearch = productDtoRepository.search(product_name,brand_id,category_id,sole_id,material_id,pageable);
        model.addAttribute("listProduct",productSearch.getContent());
        model.addAttribute("listProducts",productSearch);
        model.addAttribute("currentPage",page);
        model.addAttribute("pageSize",size);
        return "/offline/products/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        productRepository.deleteById(id);
        return "redirect:/products/";
    }

     @GetMapping("/exportFile")
    public String export(){
        List<ProductDto> productDtoList = productDtoRepository.findAllQuantityXuatFile();
        String filePath = "D:\\GiangPH34761\\DuAn\\ShoeStore\\ShoeStore\\src\\main\\resources\\static\\ExcelFile";
        String fileName = "Product";
        productDtoService.exportFile(productDtoList, filePath, fileName);
        return "redirect:/products/";
     }

}
