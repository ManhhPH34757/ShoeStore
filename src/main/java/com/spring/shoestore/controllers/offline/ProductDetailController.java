package com.spring.shoestore.controllers.offline;


import com.spring.shoestore.dto.ProductDetailDto;
import com.spring.shoestore.entity.Color;
import com.spring.shoestore.entity.Size;
import com.spring.shoestore.repo.ColorRepository;
import com.spring.shoestore.repo.ProductDetailRepository;
import com.spring.shoestore.repo.ProductRepository;
import com.spring.shoestore.repo.SizeRepository;
import com.spring.shoestore.service.ProductDetailsService;
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
    private ProductDetailRepository productDetailRepository;

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

    @GetMapping("/details/{idProduct}")
    public String getProductDetails(
            @PathVariable("idProduct") Integer idProduct,
            Model model
    ) {
        model.addAttribute("idProduced", idProduct);
//        model.addAttribute("listProductDetails", productDetailRepository.findProductDetailsByIdProduct(productRepository.findById(idProduct).get()));
        model.addAttribute("productDto", productRepository.findById(idProduct).get());
        return "/productDetails/index";
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

    @GetMapping("/delete-details/{idProduct}/{idProductDetails}")
    public String deleteProductDetails(@PathVariable("idProduct") Integer idProduct, @PathVariable Integer idProductDetails) {
        productDetailRepository.deleteById(idProductDetails);
        return "redirect:/products/details/" + idProduct;
    }

}
