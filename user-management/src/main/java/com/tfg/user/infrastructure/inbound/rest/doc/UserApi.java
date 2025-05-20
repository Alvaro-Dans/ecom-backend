package com.tfg.user.infrastructure.inbound.rest.doc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@OpenAPIDefinition
@Tag(name = "Products", description = "Gestión de productos en el catálogo")
@RequestMapping("/api/v1/users")
public interface UserApi {

    @PostMapping("/register")
    void register();

    @PostMapping("/logout")
    void logout();

    @GetMapping("/user-info")
    void userInfo();

    @GetMapping("/auth-status")
    void authStatus();

    @PostMapping("/address")
    void address();

    @PostMapping("/reset-password")
    void resetPassword();
}
