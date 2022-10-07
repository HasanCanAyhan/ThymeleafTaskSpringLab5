package com.cydeo.spring05thymeleaf.controller;

import com.cydeo.spring05thymeleaf.model.Product;
import com.cydeo.spring05thymeleaf.repository.ProductRepository;
import com.cydeo.spring05thymeleaf.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }


    @GetMapping("/list")
    public String productListPage(Model model){

        model.addAttribute("products",productService.listProduct() );

        return "product/list";

    }


    @GetMapping("/create-form")
    public String getCreateProductPage(Model model){


        model.addAttribute("product", new Product());

        return "/product/create-product";

    }

    @PostMapping("/create-product")
    public String addProduct(@ModelAttribute("product") Product product){

        productService.productCreate(product);
        productRepository.save(product);

        return "redirect:/list";
    }



}
