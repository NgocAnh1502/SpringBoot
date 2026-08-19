package com.example.product_management.mapper;

import com.example.product_management.dtos.ProductResponse;
import com.example.product_management.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductResponse toProductResponse(Product product);

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(target = "category.name", ignore = true)
    Product toEntity(ProductResponse productResponse);
}
