package com.arthursousa.inventory.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductApiTest {
    @Autowired MockMvc mvc;

    @Test
    void createsAndListsProduct() throws Exception {
        mvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""" 
                    {"name":"Filtro de óleo","sku":"FILTRO-001","price":39.90,"quantity":5}
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Filtro de óleo"));

        mvc.perform(get("/api/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].sku").value("FILTRO-001"));
    }

    @Test
    void rejectsNegativePrice() throws Exception {
        mvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"name":"Peça","sku":"NEG-01","price":-10,"quantity":1}
                    """))
            .andExpect(status().isBadRequest());
    }
}
