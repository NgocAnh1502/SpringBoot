package com.example.product_management.Repository;

import com.example.product_management.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findById(int id);

    List<Product> findByName(String name);
}
