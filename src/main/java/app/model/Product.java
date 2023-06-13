package app.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String name;
    String description;
    Long price;

    public Product(String name, String description, Long price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    protected Product() {}

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public String getShortDescription(int length) {
        return description.substring(0, length) + (description.length()>length?"...":"");
    }

    public Long getPrice() {
        return price;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Product)) return false;
        return Objects.equals(this.getId(), ((Product) obj).getId());
    }
}
