package app.repositories;

import org.springframework.data.repository.CrudRepository;
import app.model.CartItem;

import java.util.List;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    CartItem findByMyOrderIdAndProductId(Long cartId, Long productId);
    List<CartItem> findAllByMyOrderId(Long cartId);
}
