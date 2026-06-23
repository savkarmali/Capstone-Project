package com.capstone.shop.service;

import com.capstone.dto.ProductResponse;
import com.capstone.entity.Product;
import com.capstone.repository.ProductRepository;
import com.capstone.shop.service.impl.ShopServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShopServiceImplTest {

    @Test
    void getProductsShouldReturnAvailableProductsSortedByLowPrice() {
        ProductRepository repository = mock(ProductRepository.class);
        ShopService service = new ShopServiceImpl(repository);

        Product rose = getProduct(1L, "Rose Bouquet", "Love", "500.00");
        Product lily = getProduct(2L, "Lily Bouquet", "Birthday", "300.00");

        when(repository.findByAvailableTrue()).thenReturn(Arrays.asList(rose, lily));

        List<ProductResponse> response = service.getProducts("All", "price-low-to-high");

        assertEquals(2, response.size());
        assertEquals("Lily Bouquet", response.get(0).getName());
        assertEquals("Rose Bouquet", response.get(1).getName());
    }

    @Test
    void getProductsShouldFilterByCategory() {
        ProductRepository repository = mock(ProductRepository.class);
        ShopService service = new ShopServiceImpl(repository);

        Product rose = getProduct(1L, "Rose Bouquet", "Love", "500.00");
        when(repository.findByCategoryIgnoreCaseAndAvailableTrue("Love")).thenReturn(Arrays.asList(rose));

        List<ProductResponse> response = service.getProducts("Love", "name");

        assertEquals(1, response.size());
        assertEquals("Love", response.get(0).getCategory());
    }

    private Product getProduct(Long id, String name, String category, String price) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription("Fresh flowers");
        product.setCategory(category);
        product.setImageUrl("https://example.com/image.jpg");
        product.setSmallPrice(new BigDecimal(price));
        product.setStockQuantity(10);
        product.setAvailable(true);
        return product;
    }
}