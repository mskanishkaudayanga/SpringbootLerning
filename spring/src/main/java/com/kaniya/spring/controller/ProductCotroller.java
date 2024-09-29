package com.kaniya.spring.controller;

import com.kaniya.spring.exception.ProductNotFoundException;
import com.kaniya.spring.model.Product;
import com.kaniya.spring.response.ApiResponse;
import com.kaniya.spring.service.product.IProductServices;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@AllArgsConstructor
@RestController
@RequestMapping("${api.prefix}/product")
public class ProductCotroller {
    private final IProductServices productServices;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProduct(){
        try {
            List<Product> products = productServices.getAllProducts();
            return ResponseEntity.ok(new ApiResponse("Found", products));
        } catch (Exception e) {
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/product/{id}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable int id){
        try {
            Product product = productServices.getProductById(id);
            return ResponseEntity.ok(new ApiResponse("Found", product));
        } catch (ProductNotFoundException e) {
           return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));

        }
    }

}
