package com.capstone.cart.service;

import com.capstone.cart.dto.AddToCartRequest;
import com.capstone.cart.dto.CartItemResponse;
import com.capstone.cart.dto.CartSummaryResponse;
import com.capstone.cart.dto.UpdateCartItemRequest;

public interface CartService {

    CartItemResponse addToCart(AddToCartRequest request);

    CartSummaryResponse getCartByCustomerEmail(String customerEmail);

    CartItemResponse updateCartItem(Long cartItemId, UpdateCartItemRequest request);

    void deleteCartItem(Long cartItemId);
}
