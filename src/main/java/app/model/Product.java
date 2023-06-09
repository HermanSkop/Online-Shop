package app.model;

import jakarta.persistence.*;

import java.util.Set;
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Long price;

    public Product(String name, String description, Long price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    protected Product() {}
}
