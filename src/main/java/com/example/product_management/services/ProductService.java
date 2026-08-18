package com.example.product_management.services;

import com.example.product_management.dtos.ProductRequest;
import com.example.product_management.dtos.ProductResponse;
import com.example.product_management.models.Product;
import com.example.product_management.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getAllProducts(String name, String category){
        List<Product> products;

        if(name != null && category != null){
            products = productRepository.findByNameAndCategory(name,category);
        } else if (name != null) {
            products = productRepository.findByName(name);
        } else if (category != null) {
            products = productRepository.findByCategory(category);
        } else {
            products = productRepository.findAll();
        }
        return products.stream().map(ProductResponse::new).collect(Collectors.toList());
    }

    public ProductResponse getProductById(Integer id){
        Product product = findProduct(id);
        return new ProductResponse(product);
    }

    public ProductResponse addProduct(ProductRequest request){
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());

        Product savedProduct = productRepository.save(product);
        return new ProductResponse(savedProduct);
    }

    public ProductResponse updateProduct(Integer id, ProductRequest request){
        Product product = findProduct(id);

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());

        Product updatedProduct = productRepository.save(product);
        return new ProductResponse(updatedProduct);
    }

    public void deleteProduct(Integer id){
        Product product = findProduct(id);
        productRepository.delete(product);
    }

    private Product findProduct(Integer id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thay san pham voi id: " + id));
    }
}
