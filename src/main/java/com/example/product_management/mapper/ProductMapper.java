package com.example.product_management.mapper;

import com.example.product_management.dtos.ProductRequest;
import com.example.product_management.dtos.ProductResponse;
import com.example.product_management.entity.Category;
import com.example.product_management.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductResponse toProductResponse(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "request.name", target = "name")
    @Mapping(source = "request.price", target = "price")
    @Mapping(source = "category", target = "category")
    Product toEntity(ProductRequest productRequest, Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "request.name", target = "name")
    @Mapping(source = "request.price", target = "price")
    @Mapping(source = "category", target = "category")
    void updateEntityFromRequest(ProductRequest productRequest, Category category, @MappingTarget Product product);
}
