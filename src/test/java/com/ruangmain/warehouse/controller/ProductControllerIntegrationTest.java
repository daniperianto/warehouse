package com.ruangmain.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruangmain.warehouse.Util;
import com.ruangmain.warehouse.dto.product.ProductRequest;
import com.ruangmain.warehouse.libs.auth.JwtService;
import com.ruangmain.warehouse.model.Product;
import com.ruangmain.warehouse.model.User;
import com.ruangmain.warehouse.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtService jwtService;

    @MockBean
    private ProductService service;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void findAllProduct() throws Exception {
        List<Product> products = List.of(Util.jacket(), Util.motor());
        User admin = Util.admin();

        String token = jwtService.generateToken(admin);
        when(service.findAll()).thenReturn(products);

        Assertions.assertThat(token).isNotNull();
        mvc.perform(get("/api/v1/product")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(products)));
    }

    @Test
    public void addProduct() throws Exception {
        ProductRequest request = Util.productRequestJacket();
        Product jacket = Util.jacket();
        when(service.findByName(Mockito.anyString())).thenReturn(Optional.empty());
        when(service.add(request)).thenReturn(jacket);

        User admin = Util.admin();
        String token = jwtService.generateToken(admin);

        Assertions.assertThat(token).isNotNull();
        mvc.perform(post("/api/v1/product/add")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(jacket)));
    }

    @Test
    public void increaseQty() throws Exception {
        int qty = 10;
        Product jacket = Util.jacket();
        when(service.findById(Mockito.anyLong())).thenReturn(Optional.of(jacket));
        jacket.setQty(jacket.getQty() + qty);
        when(service.addProductQuantity(Mockito.any(Product.class), Mockito.anyInt()))
                .thenReturn(jacket);

        User admin = Util.admin();
        String token = jwtService.generateToken(admin);

        Assertions.assertThat(token).isNotNull();
        mvc.perform(put("/api/v1/product/2/add-quantity?qty=10")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(jacket)));
    }
}
