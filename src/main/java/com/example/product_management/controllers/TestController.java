package com.example.product_management.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Tag(name = "Test API", description = "Cac API dung de kiem tra he thong phan quyen basic Auth")
public class TestController {
    @Operation(summary = "API co ban", description = "Bat ki user nao dang nhap thanh cong deu co the goi")
    @GetMapping("/hello")
    public String hello() {
        return "Hello World! Authentication thanh cong.";
    }

    @Operation(summary = "API danh cho Admin", description = "Yeu cau tai khoan phai co role ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminOnly(){
        return "Dang nhap bang quyen ADMIN thanh cong.";
    }

    @Operation(summary = "API danh cho User", description = "Yeu cau tai khoan phai co role User")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String userOnly(){
        return "Dang nhap bang quyen USER thanh cong.";
    }
}
