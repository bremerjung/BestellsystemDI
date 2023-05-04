package de.neuefische.springordersystem.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTestsForShopController {

    @Autowired
    MockMvc mockMvc;

    @Test
    void whenGetOrders_returnEmptyOrderList_andStatusCode200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllOrders_thenReturnEmptyOrderList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    // void Methode testen
    @Test
    @Disabled
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
                        """));
    }

}