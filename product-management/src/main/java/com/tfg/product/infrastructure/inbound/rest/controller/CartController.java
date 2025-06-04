package com.tfg.product.infrastructure.inbound.rest.controller;

import com.tfg.product.application.cart.CartService;
import com.tfg.product.infrastructure.inbound.rest.dto.request.CartRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController { // TODO CartApi

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * GET /api/cart?id={cartId}
     * Si el carrito existe en Redis, lo devuelve; de lo contrario devuelve un
     * ShoppingCart vacío con el mismo id que se pasó como parámetro.
     */
    @GetMapping
    public ResponseEntity<CartRequest> getCartById(@RequestParam("id") String id) {
        CartRequest cart = cartService.getCart(id);
        if (cart == null) {
            // Si no existe, devolvemos un carrito vacío con el Id que pide el cliente
            cart = new CartRequest();
            cart.setId(id);
        }
        return ResponseEntity.ok(cart);
    }

    /**
     * POST /api/cart
     * Recibe un ShoppingCart en el body (JSON). Intenta guardar/actualizar en Redis.
     * - Si falla (p.ej. JSON no válido o Redis no lo persiste), responde 400 Bad Request.
     * - Si todo OK, devuelve el ShoppingCart recién guardado.
     */
    @PostMapping
    public ResponseEntity<CartRequest> updateCart(@RequestBody CartRequest cart) {
        CartRequest updatedCart = cartService.setCart(cart);
        if (updatedCart == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedCart);
    }

    /**
     * DELETE /api/cart?id={cartId}
     * Elimina el carrito de Redis.
     * - Si no existía o no pudo borrarse, responde 400 Bad Request con mensaje.
     * - Si se eliminó correctamente, responde 200 OK sin cuerpo.
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteCart(@RequestParam("id") String id) {
        boolean removed = cartService.deleteCart(id);
        if (!removed) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
