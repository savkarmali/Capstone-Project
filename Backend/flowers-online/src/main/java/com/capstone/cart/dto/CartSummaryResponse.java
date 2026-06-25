package com.capstone.cart.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartSummaryResponse {

    private String customerEmail;
    private List<CartItemResponse> items;
    private BigDecimal cartTotal;

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public List<CartItemResponse> getItems() {
        return items;
    }

    public void setItems(List<CartItemResponse> items) {
        this.items = items;
    }

    public BigDecimal getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(BigDecimal cartTotal) {
        this.cartTotal = cartTotal;
    }
}
