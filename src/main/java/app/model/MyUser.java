package app.model;

import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

@Entity
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    @OneToMany(mappedBy = "myUser", cascade = CascadeType.ALL)
    private List<MyOrder> myOrders;

    public MyUser(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    protected MyUser() {
        myOrders =new LinkedList<>();
    }
}
