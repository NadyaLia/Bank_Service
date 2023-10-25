package com.example.nadyalia.bankservice.product.services;

import com.example.nadyalia.bankservice.product.entity.Product;
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
        return repository.findProductsByAgreementsCountGreaterThan(quantity);
    }

    @Override
    public List<Product> findAllChangedProducts() {
        return repository.findProductsChangedWithinLastWeek();
    }
}
