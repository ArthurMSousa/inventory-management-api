package com.arthursousa.inventory.product;

import com.arthursousa.inventory.product.ProductDtos.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) { this.service = service; }

    @GetMapping
    public List<ProductResponse> list() { return service.findAll(); }

    @GetMapping("/{id}")
    public ProductResponse get(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody CreateProduct input) {
        ProductResponse result = service.create(input);
        return ResponseEntity.created(URI.create("/api/products/" + result.id())).body(result);
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody UpdateProduct input) {
        return service.update(id, input);
    }

    @PostMapping("/{id}/stock-in")
    public ProductResponse stockIn(@PathVariable Long id, @Valid @RequestBody StockMovement input) {
        return service.addStock(id, input);
    }

    @PostMapping("/{id}/stock-out")
    public ProductResponse stockOut(@PathVariable Long id, @Valid @RequestBody StockMovement input) {
        return service.removeStock(id, input);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
