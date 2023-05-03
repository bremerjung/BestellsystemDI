package de.neuefische.springordersystem.service;

import de.neuefische.springordersystem.model.Order;
import de.neuefische.springordersystem.model.Product;
import de.neuefische.springordersystem.repo.OrderRepo;
import de.neuefische.springordersystem.repo.ProductRepo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShopServiceTest {


    ProductRepo productRepo = mock(ProductRepo.class);
    OrderRepo orderRepo = mock(OrderRepo.class);
    ShopService shopService = new ShopService(productRepo, orderRepo);

    @Test
    public void testGetProduct() {
        // given
        int id = 1;
        Product expectedProduct = new Product(1, "Banane");
        when(productRepo.getProduct(id)).thenReturn(expectedProduct);

        // when
        Product actualProduct = shopService.getProduct(1);

        // then
        assertEquals(expectedProduct, actualProduct);
        verify(productRepo).getProduct(id);
    }

    @Test
    public void testListProducts() {
        // given
        List<Product> expectedProductList = new ArrayList<>();
        when(productRepo.listProducts()).thenReturn(expectedProductList);

        // when
        List<Product> actualProductList = shopService.listProducts();

        // then
        assertEquals(expectedProductList, actualProductList);
        verify(productRepo).listProducts();
    }

    @Test
    public void testAddOrder() {
        // given
        int orderId = 4711;
        List<Integer> productIds = Arrays.asList(1,2,3);
        List<Product> mockProducts = Arrays.asList(
                new Product(1, "Product 1"),
                new Product(2, "Product 2"),
                new Product(3, "Product 3")
        );
        when(productRepo.getProduct(1)).thenReturn(mockProducts.get(0));
        when(productRepo.getProduct(2)).thenReturn(mockProducts.get(1));
        when(productRepo.getProduct(3)).thenReturn(mockProducts.get(2));

        // when
        shopService.addOrder(orderId, productIds);

        // then
        verify(productRepo).getProduct(1);
        verify(productRepo).getProduct(2);
        verify(productRepo).getProduct(3);
        verify(orderRepo).addOrder(any(Order.class));
    }

    @Test
    public void testGetOrder() {
        // given
        int orderId = 4711;
        Order expectedOrder = new Order();
        when(orderRepo.getOrder(orderId)).thenReturn(expectedOrder);

        // when
        Order actualOrder = shopService.getOrder(orderId);

        // then
        assertEquals(expectedOrder, actualOrder);
        verify(orderRepo).getOrder(orderId);
    }

    @Test
    public void testListOrders() {
        // given
        List<Order> expectedOrderList = new ArrayList<>();
        when(orderRepo.listOrders()).thenReturn(expectedOrderList);

        // when
        List<Order> actualOrderList = shopService.listOrders();

        // then
        assertEquals(expectedOrderList, actualOrderList);
        verify(orderRepo).listOrders();
    }

}