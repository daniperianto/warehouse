package com.ruangmain.warehouse.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruangmain.warehouse.Util;
import com.ruangmain.warehouse.dto.auth.LoginRequest;
import com.ruangmain.warehouse.dto.auth.RegisterRequest;
import com.ruangmain.warehouse.dto.auth.ResponseUser;
import com.ruangmain.warehouse.libs.auth.JwtService;
import com.ruangmain.warehouse.model.User;
import com.ruangmain.warehouse.repository.UserRepository;
import com.ruangmain.warehouse.service.UserService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private JwtService jwtService;

    @Test
    public void registerNewAccountReturnResponseUser() throws Exception {
        RegisterRequest request = Util.registerRequestUser();
        ResponseUser user = Util.responseUser();
        when(service.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        when(service.register(Mockito.any(RegisterRequest.class))).thenReturn(user);

        mvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(mapper.writeValueAsString(user)));
    }

    @Test
    public void loginReturnToken() throws Exception {
        LoginRequest request = Util.loginRequestUser();
        String token = "token";
        when(service.login(Mockito.any(LoginRequest.class))).thenReturn(token);

        mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(token));
    }

    @Test
    public void findAllUser() throws Exception {
        List<ResponseUser> userList = List.of(Util.responseUser(), Util.responseAdmin());
        User admin = Util.admin();

        String token = jwtService.generateToken(admin);
        when(service.findAll()).thenReturn(userList);

        Assertions.assertThat(token).isNotNull();
        mvc.perform(get("/api/v1/auth/admin/all")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(userList)));
    }


}
