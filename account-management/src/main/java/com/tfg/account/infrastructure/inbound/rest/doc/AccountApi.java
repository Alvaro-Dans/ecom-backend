package com.tfg.account.infrastructure.inbound.rest.doc;

import com.tfg.account.infrastructure.inbound.rest.dto.AddressDto;
import com.tfg.account.infrastructure.inbound.rest.dto.request.LoginRequestDto;
import com.tfg.account.infrastructure.inbound.rest.dto.request.RegisterDto;
import com.tfg.account.infrastructure.inbound.rest.dto.response.UserInfoDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/api/v1/account")
public interface AccountApi {

    @Operation(summary = "Registro de usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario registrado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping("/register")
    ResponseEntity<Void> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO con datos de registro",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = RegisterDto.class),
                            examples = @ExampleObject(value = "{\"firstName\":\"Juan\",\"lastName\":\"Pérez\",\"email\":\"juan.perez@example.com\",\"password\":\"P@ssw0rd!23\"}")
                    )
            ) @RequestBody RegisterDto dto
    );

    @Operation(summary = "Obtiene información del usuario autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Información del usuario",
                    content = @Content(schema = @Schema(implementation = UserInfoDto.class))
            ),
            @ApiResponse(responseCode = "204", description = "No autenticado", content = @Content)
    })
    @GetMapping("/user-info")
    ResponseEntity<UserInfoDto> getUserInfo();

    @Operation(summary = "Consulta el estado de autenticación")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado de autenticación"),
    })
    @GetMapping("/auth-status")
    ResponseEntity<Boolean> getAuthStatus();

    @Operation(summary = "Crear o actualizar dirección del usuario autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dirección actualizada",
                    content = @Content(schema = @Schema(implementation = AddressDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content)
    })
    @PostMapping("/address")
    ResponseEntity<AddressDto> updateAddress(
            @RequestBody AddressDto dto
    );

    @Operation(summary = "Restablecer contraseña del usuario autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contraseña actualizada"),
            @ApiResponse(responseCode = "400", description = "Contraseña actual inválida", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content)
    })
    @PostMapping("/reset-password")
    ResponseEntity<Void> resetPassword(
            @Parameter(description = "Contraseña actual del usuario") @RequestParam() String currentPassword,
            @Parameter(description = "Nueva contraseña deseada") @RequestParam() String newPassword
    );

    @Operation(summary = "Autenticar usuario (login)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content)
    })
    @PostMapping("/login")
    ResponseEntity<Void> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credenciales de usuario",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = LoginRequestDto.class),
                            examples = @ExampleObject(value = "{\"email\":\"juan.perez@example.com\",\"password\":\"P@ssw0rd!23\"}")
                    )
            ) @RequestBody LoginRequestDto dto,
            HttpServletRequest request
    );

    @Operation(summary = "Cerrar sesión del usuario (logout)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Logout exitoso"),
            @ApiResponse(responseCode = "401", description = "Usuario no autenticado", content = @Content)
    })
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> logout();

    @GetMapping("/validate-session")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> validateSession();
}
