package com.example.product_management.services;

import com.example.product_management.dtos.ProductRequest;
import com.example.product_management.dtos.ProductResponse;
import com.example.product_management.entity.Category;
import com.example.product_management.entity.Product;
import com.example.product_management.mapper.ProductMapper;
import com.example.product_management.repository.CategoryRepository;
import com.example.product_management.repository.ProductRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    public Page<ProductResponse> getAllProducts(String name, String categoryName,  Pageable pageable) {
        Page<Product> productPage;

        if(name != null && categoryName != null){
            productPage = productRepository.findByNameContainingIgnoreCaseAndCategoryNameContainingIgnoreCase(name, categoryName, pageable);
        } else if (name != null) {
            productPage = productRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (categoryName != null) {
            productPage = productRepository.findByCategoryNameContainingIgnoreCase(categoryName, pageable);
        } else {
            productPage = productRepository.findAll(pageable);
        }
        return productPage.map(productMapper::toProductResponse);
    }

    public ProductResponse getProductById(Integer id){
        Product product = findProduct(id);
        return productMapper.toProductResponse(product);
    }

    public ProductResponse addProduct(ProductRequest request){
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thay Category"));
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        return productMapper.toProductResponse(savedProduct);
    }

    public ProductResponse updateProduct(Integer id, ProductRequest request){
        Product product = findProduct(id);

        product.setName(request.getName());
        product.setPrice(request.getPrice());

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy Category"));
        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);
        return productMapper.toProductResponse(updatedProduct);
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
