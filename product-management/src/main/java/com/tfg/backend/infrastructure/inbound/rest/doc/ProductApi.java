package com.tfg.backend.infrastructure.inbound.rest.doc;

import com.tfg.backend.infrastructure.inbound.rest.dto.request.ProductRequest;
import com.tfg.backend.infrastructure.inbound.rest.dto.response.PagedProductsResponse;
import com.tfg.backend.infrastructure.inbound.rest.dto.response.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Products", description = "Gestión de productos en el catálogo")
@RequestMapping("/api/v1/products")
public interface ProductApi {

    @Operation(summary = "Crear un nuevo producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto creado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void createProduct(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del producto a crear",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ProductRequest.class))
            )
            ProductRequest productRequest
    );

    @Operation(summary = "Obtener un producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ProductResponse getProduct(
            @Parameter(description = "ID del producto", required = true)
            @PathVariable UUID id
    );

    @Operation(summary = "Listar productos con paginación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de productos paginado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class)))
    })
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    PagedProductsResponse getProductsPaged(
            @Parameter(description = "Número de página (empezando en 0)", example = "0")
            @RequestParam(name = "page", defaultValue = "0") int page,

            @Parameter(description = "Tamaño de página", example = "10")
            @RequestParam(name = "size", defaultValue = "10") int size
    );



    @Operation(summary = "Actualizar un producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ProductResponse updateProduct(
            @Parameter(description = "ID del producto", required = true)
            @PathVariable UUID id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados del producto",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ProductRequest.class))
            )
            ProductRequest productRequest
    );

    @Operation(summary = "Eliminar un producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProduct(
            @Parameter(description = "ID del producto", required = true)
            @PathVariable UUID id
    );
}
