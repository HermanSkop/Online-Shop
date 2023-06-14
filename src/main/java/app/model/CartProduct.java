package app.model;

public class CartProduct extends Product{
    int quantity;
    public CartProduct(Product product, int quantity){
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
    public Long getPrice() {
        return quantity*super.getPrice();
    }
}
