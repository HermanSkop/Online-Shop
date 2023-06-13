package app.repositories;

import app.model.CartItem;
import app.model.Product;
import org.springframework.data.repository.CrudRepository;

public
    interface ProductRepository extends CrudRepository<Product, Long> {}
