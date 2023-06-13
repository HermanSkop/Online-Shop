package app.controller;

import app.model.Product;
import app.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collection;

@Controller
public
    class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @RequestMapping("/products")
    public String getSubjects(Model model){
        model.addAttribute( "products", productRepository.findAll());
        return "products";
    }
}
