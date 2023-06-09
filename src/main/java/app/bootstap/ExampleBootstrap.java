package app.bootstap;

import app.model.MyOrder;
import app.model.Product;
import app.model.MyUser;
import app.repositories.CartItemRepository;
import app.repositories.ProductRepository;
import app.repositories.MyUserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import app.model.CartItem;
import app.repositories.MyOrderRepository;

@Component
public
    class ExampleBootstrap
    implements ApplicationListener<ContextRefreshedEvent> {

    private CartItemRepository cartItemRepository;
    private MyUserRepository myUserRepository;
    private MyOrderRepository myOrderRepository;
    private ProductRepository productRepository;

    public ExampleBootstrap(CartItemRepository cartItemRepository, MyUserRepository myUserRepository,
                            MyOrderRepository myOrderRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.myUserRepository = myUserRepository;
        this.myOrderRepository = myOrderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initDB();
    }

    private void initDB(){
        Product product = new Product("peach", "just a regular peach", 17999L);
        productRepository.save(product);
        MyUser myUser = new MyUser("1","1");
        myUserRepository.save(myUser);
        MyOrder myOrder = new MyOrder(myUser);
        myOrderRepository.save(myOrder);
        CartItem cartItem = new CartItem(myOrder, product, 2);
        cartItemRepository.save(cartItem);
        myOrder.getCartItems().add(cartItem);
    }
}
