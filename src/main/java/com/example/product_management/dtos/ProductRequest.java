package com.example.product_management.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

public class ProductRequest {
    @NotBlank(message = "Ten san pham khong duoc de trong")
    private String name;

    @NotNull(message = "Gia san pham khong duoc de trong")
    @Min(value = 0, message = "Gia san pham khong duoc be hon 0")
    private Double price;

    @NotNull(message = "Id danh muc khong duoc de trong")
    private Integer categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
