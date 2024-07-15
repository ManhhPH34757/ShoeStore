package com.spring.shoestore.offline.controller;

import com.spring.shoestore.offline.dto.ProductDetailDto;
import com.spring.shoestore.offline.entity.Color;
import com.spring.shoestore.offline.entity.Size;
import com.spring.shoestore.offline.repository.ColorRepository;
import com.spring.shoestore.offline.repository.ProductDetailRepository;
import com.spring.shoestore.offline.repository.ProductRepository;
import com.spring.shoestore.offline.repository.SizeRepository;
import com.spring.shoestore.offline.service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductDetailController {
    private final ProductRepository productRepository;

    private final ProductDetailRepository productDetailsRepository;

    private final ColorRepository colorRepository;

    private final SizeRepository sizeRepository;

    private final ProductDetailsService productDetailsService;

    public ProductDetailController(ProductRepository productRepository, ProductDetailRepository productDetailsRepository, ColorRepository colorRepository, SizeRepository sizeRepository, ProductDetailsService productDetailsService) {
        this.productRepository = productRepository;
        this.productDetailsRepository = productDetailsRepository;
        this.colorRepository = colorRepository;
        this.sizeRepository = sizeRepository;
        this.productDetailsService = productDetailsService;
    }

    @ModelAttribute("colors")
    public List<Color> getColorList() {
        return colorRepository.findAll();
    }

    @ModelAttribute("sizes")
    public List<Size> getSizeList() {
        return sizeRepository.findAll();
    }

    @GetMapping("/details/{id}")
    public String getProductDetails(
            @PathVariable("id") Integer id,
            Model model
    ) {
        model.addAttribute("id", id);
        model.addAttribute("listProductDetails", productDetailsRepository.findProductDetailByIdProduct(productRepository.findById(id).get()));
        model.addAttribute("productDto", productRepository.findById(id).get());
        System.out.println(productRepository.findById(id).get());
        return "/offline/productDetail/index";
    }

    @ResponseBody
    @PostMapping("/create-details/{idProduct}")
    public ResponseEntity<String> createProductDetails(@PathVariable Integer idProduct, @RequestBody List<ProductDetailDto> productDetails) {
        try {
            productDetailsService.saveProductDetails(idProduct, productDetails);
            return ResponseEntity.ok("Products saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving products: " + e.getMessage());
        }
    }

    @GetMapping("/delete-details/{idProduct}/{id}")
    public String deleteProductDetails(@PathVariable("idProduct") Integer idProduct, @PathVariable Integer id) {
        productDetailsRepository.deleteById(id);
        return "redirect:/products/details/" + idProduct;
    }


}
