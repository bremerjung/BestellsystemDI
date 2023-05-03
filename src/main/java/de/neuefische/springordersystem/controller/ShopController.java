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
    public Order getOrder(@PathVariable int id) {
        return shopService.getOrder(id);
    }

    //post
    @PostMapping("orders/{id}")
    public void addOrder(@PathVariable int id, @RequestBody List<Integer> productIds) {
        //List<Integer> prodIds = Arrays.asList(productIds);
        shopService.addOrder(id, productIds);
    }

    //Bonus
    //delete
    @DeleteMapping("orders/{id}")
    public void deleteOrder(@PathVariable int id) {
        //shopService.deleteOrderById(id);
    }

    //Bonus 2. Erstelle einen Endpunkt, um Orders anzulegen / zu überschreiben

    @PostMapping("products")
    public void insertProduct(@RequestBody Product product) {
        //shopService.insertProduct(product);
    }

    @DeleteMapping("products/{id}")
    public void deleteProduct(@PathVariable int id) {
        //shopService.deleteProductById(id);
    }
}
