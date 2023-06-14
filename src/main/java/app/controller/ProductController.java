package app.controller;

import app.repositories.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public
    class ProductController {
    private final ProductRepository productRepository;
    private final HttpSession httpSession;
    @Autowired
    public ProductController(ProductRepository productRepository, HttpSession httpSession) {
        this.productRepository = productRepository;
        this.httpSession = httpSession;
    }
    @RequestMapping("/products")
    public String getSubjects(Model model) {
        if(httpSession.getAttribute("order_id") == null) return "login";
        model.addAttribute( "products", productRepository.findAll());
        httpSession.setAttribute("products", productRepository.findAll());
        return "products";
    }
}
