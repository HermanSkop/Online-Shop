package app.model;

import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;
@Entity
public class MyOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private MyUser myUser;
    @OneToMany(mappedBy = "myOrder", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    public MyOrder(MyUser myUser) {
        this();
        this.myUser = myUser;
    }

    protected MyOrder() {
        cartItems = new LinkedList<>();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public Long getId() {
        return id;
    }
}
