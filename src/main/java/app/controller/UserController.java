package app.controller;

import app.model.MyOrder;
import app.model.MyUser;
import app.repositories.MyUserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
@Controller
public class UserController {
    private final MyUserRepository myUserRepository;
    private final HttpSession httpSession;
    @Autowired
    public UserController(MyUserRepository myUserRepository, HttpSession httpSession) {
        this.myUserRepository = myUserRepository;
        this.httpSession = httpSession;
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    @RequestMapping("/log-in")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        if(authenticate(username, password)){
            setUsernameAndPassword(username, password);
            setOrder(username);
            return "redirect:products";
        }
        else return "login_error";
    }
    @RequestMapping("/sign-up")
    public String register(@RequestParam("username") String username, @RequestParam("password") String password) {
        if(myUserRepository.findByUsername(username) == null){
            MyUser user = new MyUser(username, password);
            myUserRepository.save(user);
            setUsernameAndPassword(username, password);
            setOrder(username);
            return "redirect:products";
        }
        else return "register_error";
    }

    private boolean authenticate(String username, String password) {
        if (myUserRepository.findByUsername(username) == null) return false;
        else return myUserRepository.findByUsername(username).getPassword().equals(password);
    }
    private void setOrder(String username){
        MyUser user = myUserRepository.findByUsername(username);
        MyOrder order;
        if (user.getMyOrders()==null || user.getMyOrders().isEmpty()) {
            order = new MyOrder(user, new LinkedList<>());
            user.getMyOrders().add(order);
            myUserRepository.save(user);
        }
        order = user.getMyOrders().get(user.getMyOrders().size()-1);
        httpSession.setAttribute("order_id", order.getId());
    }
    private void setUsernameAndPassword(String username, String password){
        httpSession.setAttribute("username", username);
        httpSession.setAttribute("password", password);
    }
}
