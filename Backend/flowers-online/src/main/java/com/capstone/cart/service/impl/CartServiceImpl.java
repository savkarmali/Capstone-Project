package com.capstone.cart.service.impl;

import com.capstone.cart.dto.AddToCartRequest;
import com.capstone.cart.dto.CartItemResponse;
import com.capstone.cart.dto.CartSummaryResponse;
import com.capstone.cart.dto.UpdateCartItemRequest;
import com.capstone.cart.entity.CartItem;
import com.capstone.cart.repository.CartItemRepository;
import com.capstone.cart.service.CartService;
import com.capstone.entity.Product;
import com.capstone.exception.ProductNotFoundException;
import com.capstone.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Override
    public CartItemResponse addToCart(AddToCartRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + request.getProductId()));

        if (!Boolean.TRUE.equals(product.getAvailable())) {
            throw new IllegalArgumentException("Product is not available");
        }

        BigDecimal selectedPrice = getPriceForSize(product, request.getSelectedSize());
        BigDecimal subtotal = selectedPrice.multiply(new BigDecimal(request.getQuantity()));

        CartItem cartItem = new CartItem();
        cartItem.setCustomerEmail(request.getCustomerEmail());
        cartItem.setProductId(product.getId());
        cartItem.setProductName(product.getName());
        cartItem.setImageUrl(product.getImageUrl());
        cartItem.setSelectedSize(request.getSelectedSize());
        cartItem.setPrice(selectedPrice);
        cartItem.setQuantity(request.getQuantity());
        cartItem.setSubtotal(subtotal);
        cartItem.setCreatedAt(LocalDateTime.now());

        CartItem savedCartItem = cartItemRepository.save(cartItem);
        return toResponse(savedCartItem);
    }

    @Override
    public CartSummaryResponse getCartByCustomerEmail(String customerEmail) {
        List<CartItemResponse> items = cartItemRepository.findByCustomerEmailIgnoreCase(customerEmail)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        BigDecimal cartTotal = items.stream()
                .map(CartItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        CartSummaryResponse response = new CartSummaryResponse();
        response.setCustomerEmail(customerEmail);
        response.setItems(items);
        response.setCartTotal(cartTotal);
        return response;
    }

    @Override
    public CartItemResponse updateCartItem(Long cartItemId, UpdateCartItemRequest request) {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found with id: " + cartItemId));

        cartItem.setQuantity(request.getQuantity());
        cartItem.setSubtotal(cartItem.getPrice().multiply(new BigDecimal(request.getQuantity())));

        CartItem savedCartItem = cartItemRepository.save(cartItem);
        return toResponse(savedCartItem);
    }

    @Override
    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found with id: " + cartItemId));
        cartItemRepository.delete(cartItem);
    }

    private BigDecimal getPriceForSize(Product product, String selectedSize) {
        if ("Small".equalsIgnoreCase(selectedSize) && product.getSmallPrice() != null) {
            return product.getSmallPrice();
        }

        if ("Medium".equalsIgnoreCase(selectedSize) && product.getMediumPrice() != null) {
            return product.getMediumPrice();
        }

        if ("Large".equalsIgnoreCase(selectedSize) && product.getLargePrice() != null) {
            return product.getLargePrice();
        }

        throw new IllegalArgumentException("Selected size is not available for this product");
    }

    private CartItemResponse toResponse(CartItem cartItem) {
        CartItemResponse response = new CartItemResponse();
        response.setId(cartItem.getId());
        response.setCustomerEmail(cartItem.getCustomerEmail());
        response.setProductId(cartItem.getProductId());
        response.setProductName(cartItem.getProductName());
        response.setImageUrl(cartItem.getImageUrl());
        response.setSelectedSize(cartItem.getSelectedSize());
        response.setPrice(cartItem.getPrice());
        response.setQuantity(cartItem.getQuantity());
        response.setSubtotal(cartItem.getSubtotal());
        response.setCreatedAt(cartItem.getCreatedAt());
        return response;
    }
}
