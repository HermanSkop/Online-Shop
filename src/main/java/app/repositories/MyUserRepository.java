package app.repositories;

import app.model.MyUser;
import org.springframework.data.repository.CrudRepository;

public
    interface MyUserRepository extends CrudRepository<MyUser, Long> {}
