package de.neuefische.springordersystem.controller;

import de.neuefische.springordersystem.model.Order;
import de.neuefische.springordersystem.model.Product;
import de.neuefische.springordersystem.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    //get
    @GetMapping("products")
    public List<Product> listProducts() {
        return shopService.listProducts();
    }

    @GetMapping("products/{id}")
    public Product getProduct(@PathVariable int id) {
        return shopService.getProduct(id);
    }

    @GetMapping("orders")
    public List<Order> listOrders() {
        return shopService.listOrders();
    }

    @GetMapping("orders/{id}")
    public Order getOrder(@PathVariable String id) {
        return shopService.getOrder(id);
    }

    //post
    @PostMapping("orders")
    public Order addOrder(@RequestBody List<Integer> productIds) {
        return shopService.addOrder(productIds);
    }

    @PostMapping("orders/addOrder2")
    public void addOrder2(@RequestBody Order order) {
        //List<Integer> prodIds = Arrays.asList(productIds);
        shopService.addOrder2(order);
    }

    //Bonus
    //delete
    @DeleteMapping("orders/{id}")
    public void deleteOrder(@PathVariable int id) {
        //shopService.deleteOrderById(id);
    }

    //Bonus 2. Erstelle einen Endpunkt, um Orders anzulegen / zu überschreiben

    @PostMapping("products")
    public void addProduct(@RequestBody Product product) {
        shopService.addProduct(product);
    }

    @DeleteMapping("products/{id}")
    public void deleteProduct(@PathVariable int id) {
        //shopService.deleteProductById(id);
    }
}
