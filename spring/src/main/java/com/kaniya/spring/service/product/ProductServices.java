package com.kaniya.spring.service.product;

import com.kaniya.spring.exception.ProductNotFoundException;
import com.kaniya.spring.model.Category;
import com.kaniya.spring.model.Product;
import com.kaniya.spring.repository.CategoryRepository;
import com.kaniya.spring.repository.ProductRepository;
import com.kaniya.spring.reqest.AddProductReqest;
import com.kaniya.spring.reqest.ProductUpadateReqest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServices implements  IProductServices {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductReqest request) {
        //check if the category is found
        //if yes set it as new category
        //if no the save it as a new category
        //set as new product category
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()->{
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request,category));
    }


    private  Product createProduct(AddProductReqest reqest, Category category) {
        return new Product(
                reqest.getName(),
                reqest.getBrand(),
                reqest.getPrice(),
                reqest.getInventory(),
                reqest.getDescription(),
                category
        );
    }
    @Override
    public Product  updateProduct(ProductUpadateReqest request,long productId) {
        return productRepository.findById(productId)
                .map(existingProduct ->UpdateExistingProduct(existingProduct,request))
                .map(productRepository::save)
                .orElseThrow(()-> new ProductNotFoundException("product not found"));
    }
    private Product UpdateExistingProduct(Product existingProduct, ProductUpadateReqest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;

    }

    @Override
    public Product getProductById(long id) {
        return productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("product not Found"));
    }

    @Override
    public void deleteProductById(long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                ()->{throw new ProductNotFoundException("product not found");});
    }


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByName(String category) {
        return productRepository.findByName(category);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }
}
