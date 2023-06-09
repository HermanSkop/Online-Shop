package app.model;

import jakarta.persistence.*;

@Entity
public
    class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MyOrder myOrder;

    @ManyToOne
    private Product product;

    private int quantity;

    public CartItem(MyOrder myOrder, Product product, int quantity) {
        this.myOrder = myOrder;
        this.product = product;
        this.quantity = quantity;
    }

    protected CartItem() {

    }
}
