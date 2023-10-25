package com.example.nadyalia.bankservice.product.services;

import com.example.nadyalia.bankservice.product.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProductsWhereAgreementsQuantityMoreThan(int quantity);

    List<Product> findAllChangedProducts();
}
