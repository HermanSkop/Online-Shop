package app.controller;

import app.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public
    class ProductController {

    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @RequestMapping("/products")
    public String getSubjects(Model model){
        model.addAttribute( "products", productRepository.findAll());
        return "products";
    }
}
