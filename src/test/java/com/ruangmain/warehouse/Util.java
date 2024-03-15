package com.ruangmain.warehouse;

import com.ruangmain.warehouse.dto.auth.LoginRequest;
import com.ruangmain.warehouse.dto.auth.RegisterRequest;
import com.ruangmain.warehouse.dto.auth.ResponseUser;
import com.ruangmain.warehouse.dto.product.ProductRequest;
import com.ruangmain.warehouse.model.Action;
import com.ruangmain.warehouse.model.Product;
import com.ruangmain.warehouse.model.ProductHistory;
import com.ruangmain.warehouse.model.User;

public class Util {

    public static User user() {
        return User.builder()
                .username("user")
                .email("user@mail.com")
                .password("user-user")
                .role("USER").build();
    }

    public static User admin() {
        return User.builder()
                .username("admin")
                .email("admin@mail.com")
                .password("admin1234")
                .role("ADMIN").build();
    }

    public static Product jacket() {
        return Product.builder()
                .name("jacket")
                .qty(10)
                .type("garment").build();
    }

    public static Product motor() {
        return Product.builder()
                .name("motor")
                .qty(2)
                .type("vehicle").build();
    }

    public static ProductHistory sellJacket(Product product) {
        return ProductHistory.builder()
                .product(product)
                .qty(5)
                .description("selling")
                .action(Action.USING).build();
    }

    public static ProductHistory borrowMotor(Product product) {
        return ProductHistory.builder()
                .product(product)
                .qty(1)
                .description("borrowing")
                .action(Action.BORROW).build();
    }

    public static RegisterRequest registerRequestUser() {
        return new RegisterRequest(
                "user",
                "user@mail.com",
                "user-user",
                "USER");
    }

    public static ResponseUser responseUser() {
        return new ResponseUser(
                1L,
                "user",
                "user@mail.com");
    }

    public static ResponseUser responseAdmin() {
        return new ResponseUser(2L, "admin", "admin@mail.com");
    }

    public static LoginRequest loginRequestUser() {
        return new LoginRequest(
                "user@mail.com",
                "user-user");
    }

    public static ProductRequest productRequestJacket() {
        return new ProductRequest(
                "jacket",
                10,
                "garment");
    }
}
