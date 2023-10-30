package com.example.nadyalia.bankservice.product.repository;

import com.example.nadyalia.bankservice.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT product.* FROM product WHERE id IN " +
            "(SELECT product_id FROM agreement GROUP BY product_id HAVING COUNT(*) > ?1)", nativeQuery = true)
    List<Product> findProductsByAgreementsCountGreaterThan(int quantity);

    @Query(value = "SELECT product.* FROM product WHERE updated_at > DATE_SUB(NOW(), INTERVAL 7 DAY)", nativeQuery = true)
    List<Product> findProductsChangedWithinLastWeek();
}
