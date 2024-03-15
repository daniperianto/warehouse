package com.ruangmain.warehouse.service;


import com.ruangmain.warehouse.Util;
import com.ruangmain.warehouse.dto.auth.LoginRequest;
import com.ruangmain.warehouse.dto.auth.RegisterRequest;
import com.ruangmain.warehouse.dto.auth.ResponseUser;
import com.ruangmain.warehouse.libs.auth.JwtService;
import com.ruangmain.warehouse.mapper.impl.UserMapper;
import com.ruangmain.warehouse.model.User;
import com.ruangmain.warehouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;

@Log4j2
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper mapper;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService service;


    @Test
    public void userServiceRegisterReturnUser() {
        User user = Util.user();
        RegisterRequest request = Util.registerRequestUser();
        ResponseUser responseUser = Util.responseUser();

        when(repository.save(Mockito.any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(Mockito.anyString())).thenReturn("hash password");
        when(mapper.mapTo(Mockito.any(User.class))).thenReturn(responseUser);
        ResponseUser responseUserReturned = service.register(request);

        Assertions.assertThat(responseUserReturned).isNotNull();
        Assertions.assertThat(responseUserReturned).isEqualTo(responseUser);
    }

    @Test
    public void userServiceFindByEmailReturnOptionalUser() {
        Optional<User> user = Optional.of(Util.user());
        when(repository.findByEmail(Mockito.anyString())).thenReturn(user);
        Optional<User> userResult = service.findByEmail(Mockito.anyString());

        Assertions.assertThat(userResult).isEqualTo(user);

    }

    @Test
    public void userServiceLoginReturnToken() {
        LoginRequest request = Util.loginRequestUser();
        Optional<User> user = Optional.of(Util.user());
        Authentication authentication = Mockito.mock(Authentication.class);

        when(authenticationManager.authenticate(
                Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(repository.findByEmail(Mockito.anyString())).thenReturn(user);
        when(jwtService.generateToken(Mockito.any(User.class))).thenReturn("token");
        String token = service.login(request);

        Assertions.assertThat(token).isNotNull();
    }

    @Test
    public void userServiceFindByIdReturnOptionalUser() {
        Optional<User> user = Optional.of(Util.user());

        when(repository.findById(Mockito.anyLong())).thenReturn(user);
        Optional<User> userResult = service.findById(Mockito.anyLong());

        Assertions.assertThat(userResult).isEqualTo(user);
    }

    @Test
    public void userServiceFindAllReturnUsers() {
        List<User> users = List.of(Util.user(), Util.admin());
        List<ResponseUser> responseUsers = List.of(Util.responseUser(), Util.responseAdmin());

        log.info("users size: " + users.size());
        when(repository.findAll()).thenReturn(users);
        when(mapper.mapTo(users)).thenReturn(responseUsers);
        List<ResponseUser> usersResult = service.findAll();
        log.info("result: " + usersResult);
        Assertions.assertThat(usersResult.size()).isEqualTo(2);
    }

}
