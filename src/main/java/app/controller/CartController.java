package app.controller;

import app.model.CartItem;
import app.model.CartProduct;
import app.model.MyOrder;
import app.model.Product;
import app.repositories.CartItemRepository;
import app.repositories.MyOrderRepository;
import app.repositories.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Controller
public
class CartController {
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final MyOrderRepository myOrderRepository;
    private final HttpSession httpSession;
    @Autowired
    public CartController(ProductRepository productRepository, CartItemRepository cartItemRepository, MyOrderRepository myOrderRepository, HttpSession httpSession) {
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.myOrderRepository = myOrderRepository;
        this.httpSession = httpSession;
    }

    @RequestMapping("/cart/add/{id}/{quantity}")
    public String addItem(@PathVariable("id") Long productId, @PathVariable("quantity") int quantity){
        if(httpSession.getAttribute("order_id") == null) return "login";
        long currentOrderId = (long) httpSession.getAttribute("order_id");
        try {
            addProductToCart(
                    productRepository.findById(productId).orElseThrow(),
                    myOrderRepository.findById(currentOrderId).orElseThrow(),
                    quantity);
        }
        catch (NoSuchElementException e) {
            return "login";
        }
        return "redirect:../../../products";
    }

    @RequestMapping("/cart/remove/{id}/{quantity}")
    public String removeItem(@PathVariable("id") Long productId, @PathVariable("quantity") int quantity){
        if(httpSession.getAttribute("order_id") == null) return "login";
        long currentOrderId = (long) httpSession.getAttribute("order_id");
        try {
            removeFromCart(
                    productRepository.findById(productId).orElseThrow(),
                    myOrderRepository.findById(currentOrderId).orElseThrow(),
                    quantity);
        }
        catch (NoSuchElementException e) {
            return "login";
        }
        return "redirect:../../../cart";
    }

    @RequestMapping("/cart")
    public String cart(Model model){
        if(httpSession.getAttribute("order_id") == null)
            return "login";
        long currentOrderId = (long) httpSession.getAttribute("order_id");
        try {
            model.addAttribute( "cart_products", getProductsInCart(myOrderRepository.findById(currentOrderId).orElseThrow()));
        }
        catch (NoSuchElementException e) {
            return "login";
        }
        return "cart";
    }
    private void addProductToCart(Product product, MyOrder order, int quantity){
        CartItem cartItem = cartItemRepository.findByMyOrderIdAndProductId(order.getId(), product.getId());
        if(cartItem == null)
            cartItemRepository.save(new CartItem(order, product, quantity));
        else {
            cartItem.setQuantity(
                    cartItemRepository.findByMyOrderIdAndProductId(order.getId(), product.getId()).getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        }
    }
    private void removeFromCart (Product product, MyOrder order, int quantity){
        CartItem cart = cartItemRepository.findByMyOrderIdAndProductId(order.getId(), product.getId());
        if(cart != null){
            if(cart.getQuantity()-quantity <= 0)
                cartItemRepository.delete(cart);
            else {
                cart.setQuantity(cart.getQuantity() - quantity);
                cartItemRepository.save(cart);
            }
        }
    }
    private ArrayList<CartProduct> getProductsInCart(MyOrder order){
        List<CartItem> cartItems = cartItemRepository.findAllByMyOrderId(order.getId());
        return cartItems
                .stream()
                .map(cartItem -> new CartProduct(cartItem.getProduct(), cartItem.getQuantity()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}