package com.example.nadyalia.bankservice.product.services;

import com.example.nadyalia.bankservice.product.entity.Product;
import com.example.nadyalia.bankservice.product.exceptions.ProductNotFoundException;
import com.example.nadyalia.bankservice.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository repository;

    @Override
    public List<Product> findAllProductsWhereAgreementsQuantityMoreThan(int quantity) {
        List<Product> products = repository.findProductsByAgreementsCountGreaterThan(quantity);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found with agreements count greater than " + quantity);
        }
        return products;
    }

    @Override
    public List<Product> findAllChangedProducts() {
        List<Product> products = repository.findProductsChangedWithinLastWeek();
        if (products.isEmpty()) {
            throw new ProductNotFoundException("There are no changed within last week products");
        }
        return products;
    }
}
