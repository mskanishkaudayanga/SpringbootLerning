package com.kaniya.spring.service.product;

import com.kaniya.spring.model.Product;
import com.kaniya.spring.reqest.AddProductReqest;
import com.kaniya.spring.reqest.ProductUpadateReqest;

import java.util.List;

public interface IProductServices {
    Product addProduct(AddProductReqest product);
    Product getProductById(long id);
    void deleteProductById(long id);
    Product updateProduct(ProductUpadateReqest product,long id);
    List<Product> getAllProducts();
    List<Product> getProductsByName(String name);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByBrandAndName(String brand, String name);
    long countProductsByBrandAndName(String brand, String name);

}
