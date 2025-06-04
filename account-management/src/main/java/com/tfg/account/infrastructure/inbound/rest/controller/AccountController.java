package com.tfg.account.infrastructure.inbound.rest.controller;

import com.tfg.account.application.UserService;
import com.tfg.account.domain.address.Address;
import com.tfg.account.domain.user.User;
import com.tfg.account.infrastructure.inbound.mapper.ControllerMapper;
import com.tfg.account.infrastructure.inbound.rest.doc.AccountApi;
import com.tfg.account.infrastructure.inbound.rest.dto.AddressDto;
import com.tfg.account.infrastructure.inbound.rest.dto.request.LoginRequestDto;
import com.tfg.account.infrastructure.inbound.rest.dto.request.RegisterDto;
import com.tfg.account.infrastructure.inbound.rest.dto.response.UserInfoDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController implements AccountApi {
    private final UserService userService;
    private final ControllerMapper mapper;
    private final AuthenticationManager authenticationManager;

    public AccountController(UserService userService, ControllerMapper mapper, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.mapper = mapper;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public ResponseEntity<Void> register(RegisterDto dto) {
        userService.register(dto.firstName(), dto.lastName(), dto.email(), dto.password());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<UserInfoDto> getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.noContent().build();
        }
        String email = auth.getName();
        User user = userService.findByEmail(email).orElseThrow();
        UserInfoDto dto = mapper.toUserInfoDto(user);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Boolean> getAuthStatus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuth = auth != null && auth.isAuthenticated();
        return ResponseEntity.ok(isAuth);
    }

    @Override
    public ResponseEntity<AddressDto> updateAddress(AddressDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Address address = mapper.toAddress(dto);
        Address updated = userService.createOrUpdateAddress(email, address).getAddress();
        return ResponseEntity.ok(mapper.toDto(updated));
    }

    @Override
    public ResponseEntity<Void> resetPassword(String currentPassword, String newPassword) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        userService.resetPassword(email, currentPassword, newPassword);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDto dto, HttpServletRequest request) {
        // 1) autentica
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));

        // 2) guarda en el contexto
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        // 3) fuerza la creación de la sesión
        HttpSession session = request.getSession(true);
        // 4) ¡inserta el SecurityContext bajo la clave que Spring usa por defecto!
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                sc
        );

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }

    /**
     * Este endpoint valida simplemente que la sesión (JSESSIONID) sea de un usuario autenticado.
     * Si el cliente envía la cookie JSESSIONID y la sesión existe y está autenticada → 200 OK.
     * Si no hay sesión o está caducada → 401 Unauthorized (Spring lo manejará automáticamente).
     */
    @Override
    public ResponseEntity<Void> validateSession(Authentication auth) {
        return ResponseEntity.noContent().build();
    }
}
