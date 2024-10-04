package com.kaniya.spring.controller;

import com.kaniya.spring.exception.ProductNotFoundException;
import com.kaniya.spring.exception.ResouceNotFoundException;
import com.kaniya.spring.model.Product;
import com.kaniya.spring.reqest.AddProductReqest;
import com.kaniya.spring.reqest.ProductUpadateReqest;
import com.kaniya.spring.response.ApiResponse;
import com.kaniya.spring.service.product.IProductServices;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public  ResponseEntity<ApiResponse> addProduct(@RequestBody  AddProductReqest product){
        try {
            System.out.println(product);
            Product newProduct = productServices.addProduct(product);
            System.out.println(newProduct);
            return  ResponseEntity.ok(new ApiResponse("Added", newProduct));
        } catch (Exception e) {
           return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PutMapping("/Product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpadateReqest products , @PathVariable  long productId){
        try {
            Product theProduct =productServices.updateProduct(products,productId);
            return ResponseEntity.ok(new ApiResponse("Updated", theProduct));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        try {
            productServices.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Deleted",null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("product/by/brand-and-name")
    public ResponseEntity<ApiResponse> findProductByBrandAndName(@RequestParam String brand,@RequestParam String name){

        try {
            List<Product> products = productServices.getProductsByBrandAndName(brand,name);
            if (products.isEmpty()){
                return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("Found", products));
        } catch (Exception e) {
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }



    @GetMapping("product/by/category-and-brand")
    public ResponseEntity<ApiResponse> findProductByCategoryAndBrand(@RequestParam String category,@RequestParam String brand){

        try {
            List<Product> products = productServices.getProductsByCategoryAndBrand(category,brand);
            if (products.isEmpty()){
                return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("Found", products));
        } catch (Exception e) {
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @GetMapping("/product/{name}/products")
    public ResponseEntity<ApiResponse> findProductByName(@PathVariable String name){

        try {
            List<Product> products = productServices.getProductsByName(name);
            if (products.isEmpty()){
                return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("Found", products));
        } catch (Exception e) {
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand){

        try {
            List<Product> products = productServices.getProductsByBrand(brand);
            if (products.isEmpty()){
                return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("Found", products));
        } catch (Exception e) {
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category){

        try {
            List<Product> products = productServices.getProductsByCategory(category);
            if (products.isEmpty()){
                return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("Found", products));
        } catch (Exception e) {
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    public ResponseEntity<ApiResponse> countProductByBrandAndName(@RequestParam String brand,@RequestParam String name){
        try{
            var productcount =productServices.countProductsByBrandAndName(brand,name);
            return  ResponseEntity.ok(new ApiResponse("Found", productcount));
        } catch (Exception e) {
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
