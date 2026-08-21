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
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public ProductResponse addProduct(ProductRequest request){
        Category category = findCategory(request.getCategoryId());

        Product product = productMapper.toEntity(request, category);
        Product savedProduct = productRepository.save(product);
        return productMapper.toProductResponse(savedProduct);
    }

    @Transactional
    public ProductResponse updateProduct(Integer id, ProductRequest request){
        Category category = findCategory(request.getCategoryId());
        Product product = findProduct(id);

        productMapper.updateEntityFromRequest(request, category, product);

        Product updatedProduct = productRepository.save(product);
        return productMapper.toProductResponse(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Integer id){
        Product product = findProduct(id);
        productRepository.delete(product);
    }

    private Product findProduct(Integer id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thay san pham voi id: " + id));
    }

    private Category findCategory(Integer categoryId){
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thay category"));
    }
}
