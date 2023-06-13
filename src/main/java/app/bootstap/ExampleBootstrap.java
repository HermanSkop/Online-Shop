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
        Product peach = new Product("peach", "just a regular peach", 17999L);
        productRepository.save(peach);
        MyUser myUser1 = new MyUser("1","1");
        myUserRepository.save(myUser1);
        MyOrder myOrder1 = new MyOrder(myUser1);
        myOrderRepository.save(myOrder1);
        CartItem cartItem1 = new CartItem(myOrder1, peach, 2);
        cartItemRepository.save(cartItem1);
        myOrder1.getCartItems().add(cartItem1);

        Product apple = new Product("apple", """
                Apple, scientifically known as Malus domestica, is a delightful and versatile fruit that has captivated human taste buds for centuries.
                """, 999999L);
        productRepository.save(apple);
    }
}
