package app.repositories;

import app.model.MyUser;
import org.springframework.data.repository.CrudRepository;

import javax.swing.*;

public
    interface MyUserRepository extends CrudRepository<MyUser, Long> {
    MyUser findByUsername(String username);
}
