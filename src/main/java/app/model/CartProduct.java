package app.model;

import java.util.List;

public class CartProduct extends Product{
    int quantity;
    public CartProduct(List<Product> products){
        if(products != null && products.size() > 0) {
            this.name = products.get(0).getName();
            this.description = products.get(0).getDescription();
            this.price = products.get(0).getPrice();
            this.quantity = products.size();
        }
        else throw new IllegalArgumentException("CartProduct constructor requires a non-empty list of products");
    }

    public int getQuantity() {
        return quantity;
    }
    public Long getPrice() {
        return quantity*super.getPrice();
    }
}
