package com.tfg.product.application.cart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfg.product.infrastructure.inbound.rest.dto.request.CartRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class CartService { // TODO refactor use case packages

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;
    private static final Duration CART_TTL = Duration.ofDays(30);

    public CartService(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Obtiene el carrito desde Redis (clave = id). Si no existe, devuelve null.
     */
    public CartRequest getCart(String id) {
        String data = redisTemplate.opsForValue().get(id);
        if (data == null || data.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(data, CartRequest.class);
        } catch (JsonProcessingException e) {
            // Si falla la deserialización, eliminamos la clave y devolvemos null.
            redisTemplate.delete(id);
            return null;
        }
    }

    /**
     * Guarda o actualiza el ShoppingCart en Redis bajo la clave = cart.getId().
     * El TTL se fija en 30 días. Si falla el set, devuelve null; en caso contrario,
     * recarga el carrito desde Redis para devolver la versión persistida.
     */
    public CartRequest setCart(CartRequest cart) {
        try {
            String payload = objectMapper.writeValueAsString(cart);
            Boolean created = redisTemplate.opsForValue()
                    .setIfAbsent(cart.getId(), payload, CART_TTL);
            if (Boolean.FALSE.equals(created)) {
                // Si ya existía, sobrescribimos con expire renovado:
                redisTemplate.opsForValue().set(cart.getId(), payload, CART_TTL);
            }
            // Al final devolvemos la versión actualizada desde Redis:
            return getCart(cart.getId());
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * Elimina el carrito de Redis (clave = id). Devuelve true si la clave existía y fue borrada;
     * false en caso contrario.
     */
    public boolean deleteCart(String id) {
        return Boolean.TRUE.equals(redisTemplate.delete(id));
    }
}
