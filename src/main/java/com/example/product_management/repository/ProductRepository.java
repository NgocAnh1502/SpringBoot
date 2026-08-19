package com.example.product_management.repository;

import com.example.product_management.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Product> findByCategoryNameContainingIgnoreCase(String categoryName, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCaseAndCategoryNameContainingIgnoreCase(String name, String categoryName, Pageable pageable);
}
