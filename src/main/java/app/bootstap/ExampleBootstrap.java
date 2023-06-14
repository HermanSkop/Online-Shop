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

import java.util.List;

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
        Product banana = new Product("banana", "ripe and delicious banana", 9999L);
        Product orange = new Product("orange", "refreshing citrus fruit", 12999L);
        Product watermelon = new Product("watermelon", "juicy summer fruit", 24999L);
        Product strawberry = new Product("strawberry", "sweet and tangy berries", 8999L);
        Product pineapple = new Product("pineapple", "tropical fruit", 19999L);
        Product mango = new Product("mango", "sweet and juicy tropical fruit", 29999L);
        Product kiwi = new Product("kiwi", "small and sour fruit", 4999L);
        Product lemon = new Product("lemon", "sour citrus fruit", 9999L);
        Product grape = new Product("grape", "sweet and juicy berries", 5999L);
        Product cherry = new Product("cherry", "sweet and juicy berries", 6999L);
        Product pear = new Product("pear", "just a regular pear", 14999L);
        Product plum = new Product("plum", "just a regular plum", 12999L);
        Product apricot = new Product("apricot", "just a regular apricot", 19999L);
        Product nectarine = new Product("nectarine", "just a regular nectarine", 19999L);
        Product papaya = new Product("papaya", "just a regular papaya", 29999L);
        Product lychee = new Product("lychee", "just a regular lychee", 39999L);

        productRepository.saveAll(List.of(apple, banana, orange, watermelon, strawberry, pineapple, mango, kiwi, lemon,
                grape, cherry, pear, plum, apricot, nectarine, papaya, lychee));
    }
}
