package com.arthursousa.inventory.product;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public final class ProductDtos {
    private ProductDtos() { }

    public record CreateProduct(
            @NotBlank String name,
            @NotBlank String sku,
            @NotNull @DecimalMin("0.0") BigDecimal price,
            @Min(0) int quantity) { }

    public record UpdateProduct(
            @NotBlank String name,
            @NotNull @DecimalMin("0.0") BigDecimal price) { }

    public record StockMovement(@Min(1) int quantity) { }

    public record ProductResponse(Long id, String name, String sku, BigDecimal price, int quantity) {
        public static ProductResponse from(Product p) {
            return new ProductResponse(p.getId(), p.getName(), p.getSku(), p.getPrice(), p.getQuantity());
        }
    }
}
