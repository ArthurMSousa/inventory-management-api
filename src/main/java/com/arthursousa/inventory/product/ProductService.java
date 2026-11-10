package com.arthursousa.inventory.product;

import com.arthursousa.inventory.product.ProductDtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return repository.findAll().stream().map(ProductResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) { return ProductResponse.from(findEntity(id)); }

    @Transactional
    public ProductResponse create(CreateProduct input) {
        String sku = input.sku().trim();
        if (repository.existsBySku(sku)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "SKU já cadastrado");
        }
        Product p = new Product(input.name().trim(), sku, input.price(), input.quantity());
        return ProductResponse.from(repository.save(p));
    }

    @Transactional
    public ProductResponse update(Long id, UpdateProduct input) {
        Product p = findEntity(id);
        p.update(input.name().trim(), input.price());
        return ProductResponse.from(p);
    }

    @Transactional
    public ProductResponse addStock(Long id, StockMovement input) {
        Product p = findEntity(id);
        if (p.getQuantity() > Integer.MAX_VALUE - input.quantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade excede o limite");
        }
        p.setQuantity(p.getQuantity() + input.quantity());
        return ProductResponse.from(p);
    }

    @Transactional
    public ProductResponse removeStock(Long id, StockMovement input) {
        Product p = findEntity(id);
        if (p.getQuantity() < input.quantity()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Estoque insuficiente");
        }
        p.setQuantity(p.getQuantity() - input.quantity());
        return ProductResponse.from(p);
    }

    @Transactional
    public void delete(Long id) {
        Product p = findEntity(id);
        repository.delete(p);
    }

    private Product findEntity(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }
}
