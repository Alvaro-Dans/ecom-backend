package com.tfg.payment.infrastructure.inbound.rest.dto.request;

public class CreatePaymentIntentRequest {
    private String cartId;
    private long amount;
    private String currency;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
