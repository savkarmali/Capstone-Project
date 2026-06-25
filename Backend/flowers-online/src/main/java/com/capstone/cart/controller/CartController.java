package com.capstone.cart.controller;

import com.capstone.cart.dto.AddToCartRequest;
import com.capstone.cart.dto.CartItemResponse;
import com.capstone.cart.dto.CartSummaryResponse;
import com.capstone.cart.dto.UpdateCartItemRequest;
import com.capstone.cart.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:4200")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/items")
    public ResponseEntity<CartItemResponse> addToCart(@Valid @RequestBody AddToCartRequest request) {
        CartItemResponse response = cartService.addToCart(request);
        return new ResponseEntity<CartItemResponse>(response, HttpStatus.CREATED);
    }

    @GetMapping("/items")
    public ResponseEntity<CartSummaryResponse> getCartByCustomerEmail(@RequestParam String customerEmail) {
        return ResponseEntity.ok(cartService.getCartByCustomerEmail(customerEmail));
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<CartItemResponse> updateCartItem(
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemRequest request) {
        return ResponseEntity.ok(cartService.updateCartItem(cartItemId, request));
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long cartItemId) {
        cartService.deleteCartItem(cartItemId);
        return ResponseEntity.noContent().build();
    }
}
