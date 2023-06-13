package app.repositories;

import org.springframework.data.repository.CrudRepository;
import app.model.CartItem;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    CartItem findByMyOrderIdAndProductId(Long cartId, Long productId);
    Iterable<CartItem> findCartItemByMyOrderIdAndProductId(Long cartId, Long productId);
}
