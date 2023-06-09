package app.repositories;

import app.model.MyOrder;
import org.springframework.data.repository.CrudRepository;

public
    interface MyOrderRepository extends CrudRepository<MyOrder, Long> {}
