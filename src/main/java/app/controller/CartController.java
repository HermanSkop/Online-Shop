package app.controller;

import app.model.CartItem;
import app.model.CartProduct;
import app.model.MyOrder;
import app.model.Product;
import app.repositories.CartItemRepository;
import app.repositories.MyOrderRepository;
import app.repositories.ProductRepository;
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

    public CartController(ProductRepository productRepository, CartItemRepository cartItemRepository, MyOrderRepository myOrderRepository) {
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.myOrderRepository = myOrderRepository;
    }

    @RequestMapping("/cart/add/{id}/{quantity}")
    public String addItem(@PathVariable("id") Long productId, @PathVariable("quantity") int quantity, Model model){
        if(model.getAttribute("order_id") == null) return "login";
        long currentOrderId = (long) model.getAttribute("order_id");
        try {
            addProductToCart(
                    productRepository.findById(productId).orElseThrow(),
                    myOrderRepository.findById(currentOrderId).orElseThrow(),
                    quantity);
        }
        catch (NoSuchElementException e) {
            return "login";
        }
        return "redirect:cart";
    }

    @RequestMapping("/cart/remove/{id}/{quantity}")
    public String removeItem(@PathVariable("id") Long productId, @PathVariable("quantity") int quantity, Model model){
        if(model.getAttribute("order_id") == null) return "login";
        long currentOrderId = (long) model.getAttribute("order_id");
        try {
            removeFromCart(
                    productRepository.findById(productId).orElseThrow(),
                    myOrderRepository.findById(currentOrderId).orElseThrow(),
                    quantity);
        }
        catch (NoSuchElementException e) {
            return "login";
        }
        return "redirect:cart";
    }

    @RequestMapping("/cart")
    public String removeItem(Model model){
        if(model.getAttribute("order_id") == null) // TODO: finish login first
            return "login";
        long currentOrderId = (long) model.getAttribute("order_id");
        try {
            model.addAttribute( "cart_products", getProductsInCart(myOrderRepository.findById(currentOrderId).orElseThrow()));
        }
        catch (NoSuchElementException e) {
            return "login";
        }
        return "cart";
    }
    private void addProductToCart(Product product, MyOrder order, int quantity){
        if(cartItemRepository.findByMyOrderIdAndProductId(order.getId(), product.getId()) == null)
            cartItemRepository.save(new CartItem(order, product, quantity));
        else
            cartItemRepository.findByMyOrderIdAndProductId(order.getId(), product.getId()).setQuantity(
                    cartItemRepository.findByMyOrderIdAndProductId(order.getId(), product.getId()).getQuantity() + quantity);
    }
    private void removeFromCart (Product product, MyOrder order, int quantity){
        if(cartItemRepository.findByMyOrderIdAndProductId(order.getId(), product.getId()) != null){
            CartItem cart = cartItemRepository.findByMyOrderIdAndProductId(order.getId(), product.getId());
            if(cart.getQuantity()-quantity <= 0)
                cartItemRepository.delete(cart);
            else
                cart.setQuantity(cart.getQuantity()-quantity);
            }
    }

    private ArrayList<CartProduct> getProductsInCart(MyOrder order){
        List<CartItem> cartItems = (List<CartItem>) cartItemRepository.findCartItemByMyOrderIdAndProductId(order.getId(), order.getId());
        return cartItems
                .stream()
                .map(CartItem::getProduct)
                .collect(Collectors.groupingBy(p -> p))
                .values()
                .stream()
                .map(CartProduct::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}