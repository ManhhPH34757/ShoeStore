package com.spring.shoestore.offline.controller;

import com.spring.shoestore.offline.dto.ProductDetailDto;
import com.spring.shoestore.offline.entity.Color;
import com.spring.shoestore.offline.entity.Size;
import com.spring.shoestore.offline.repository.ColorRepository;
import com.spring.shoestore.offline.repository.ProductDetailRepository;
import com.spring.shoestore.offline.repository.ProductRepository;
import com.spring.shoestore.offline.repository.SizeRepository;
import com.spring.shoestore.offline.services.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductDetailController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDetailRepository productDetailsRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ProductDetailsService productDetailsService;

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
