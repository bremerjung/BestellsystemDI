package de.neuefische.springordersystem.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.neuefische.springordersystem.model.Order;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTestsForShopController {

    @Autowired
    MockMvc mockMvc;

    @Test
    void whenListProducts_thenReturn4Products_andStatusCode200() throws Exception {
        /*String response = */
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(status().isOk()).andExpect(content().json("""
                        [
                            {
                                "id": 1,
                                "name": "Apfel"
                            },
                            {
                                "id": 2,
                                "name": "Banane"
                            },
                            {
                                "id": 3,
                                "name": "Zitrone"
                            },
                            {
                                "id": 4,
                                "name": "Mandarine"
                            }
                        ]
                        """))/*.toString()*/;
        //System.out.println(response);
    }

    @Test
    void whenGetProduct_ThenReturnProduct_AndStatusCode200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/1"))
                .andExpect(status().isOk()).andExpect(content().json("""
                        {
                            "id": 1,
                            "name": "Apfel"
                        }
                        """));
    }

    @Test
    void whenListOrders_thenReturnEmptyOrderList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void whenGetOrderById_thenReturn200OK_returnCorrectOrder() throws Exception {
        // test setup: add order
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        // .contentType("application/json") // alternative Schreibweise
                        .content("[1]"))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        // test setup: extract id from created order
        String id = null;

            ObjectMapper objectMapper = new ObjectMapper();
            Order order = objectMapper.readValue(content, Order.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/" + order.getId()))
                .andExpect(status().isOk()).
                andExpect(content().json("""
                        {
                            "products": [
                                {
                                    "id": 1,
                                    "name": "Apfel"
                                }
                            ]
                        }
                        """)).andExpect(jsonPath("$.id").value(order.getId()));
    }

    @Test
    @DirtiesContext
    void whenAddOrderWithProductIds_then200OK_() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1]"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "products": [
                                {
                                    "id": 1,
                                    "name": "Apfel"
                                }
                            ]
                        }
                                                """))
                .andExpect(jsonPath("$.id").isNotEmpty());;
    }

    @Test
    @DirtiesContext
    void whenAddProduct_ThenReturn200OK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "id": 5,
                            "name": "Orange"
                        }
                        """))
                .andExpect(status().isOk());
    }


    //////////
    @Test
    @Disabled // void_Methode testen
    @DirtiesContext
    void whenAddOrderWithProductIds_then200OK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1]"))
                .andExpect(status().isOk()); // erstmal nur getestet, dass der Endpunkt ansprechbar ist

        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                             {
                                 //"id": "4f76c722-7538-4cd1-93c4-2d4132c9d15d",
                                 "products": [
                                     {
                                         "id": 1,
                                         "name": "Apfel"
                                     }
                                 ]
                             }
                         ]
                        """))
                .andExpect(jsonPath("$[0].id").isNotEmpty());
    }

}