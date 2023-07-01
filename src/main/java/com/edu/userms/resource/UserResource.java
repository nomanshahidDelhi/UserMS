package com.edu.userms.resource;

import com.edu.userms.model.User;
import com.edu.userms.repo.UserRepo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
public class UserResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private UserRepo repo;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        LOGGER.info("calling getAllUsers in Database");
        return repo.findAll();
    }

    @GetMapping("/users/{id}")
    public User getSingleUser(@PathVariable Integer id) {
        LOGGER.info("calling getSingleUser in Database");
        Optional<User> userOpt = repo.findById(id);
        if(userOpt.isPresent()) {
            return userOpt.get();
        }

        return null;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        LOGGER.info("calling createUser in Database");
        User savedUser = repo.save(user);
        return savedUser;
    }

    @GetMapping("/hello")
    public String getHello() {
        return "Hello World!";
    }

    @GetMapping("/users-orders")
    @HystrixCommand(fallbackMethod = "getOrdersFromFallback")
    public String getOrders() {
        LOGGER.info("calling orderms");
        String response = restTemplate.getForObject("http://orderms/orders", String.class);
        return response;
    }

    private String getOrdersFromFallback() {
        LOGGER.info("fallback invoked");
        return "Fallback invoked";
    }

}
