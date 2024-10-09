package com.kaniya.spring.service.product;

import com.kaniya.spring.dto.ImageDto;
import com.kaniya.spring.dto.ProductDto;
import com.kaniya.spring.exception.ProductNotFoundException;
import com.kaniya.spring.model.Category;
import com.kaniya.spring.model.Image;
import com.kaniya.spring.model.Product;
import com.kaniya.spring.repository.CategoryRepository;
import com.kaniya.spring.repository.ImageRepository;
import com.kaniya.spring.repository.ProductRepository;
import com.kaniya.spring.reqest.AddProductReqest;
import com.kaniya.spring.reqest.ProductUpadateReqest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServices implements  IProductServices {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private  final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

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


    private  Product createProduct(AddProductReqest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
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

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products){
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                 .toList();
        productDto.setImages(imageDtos);
        return productDto;
    }


}
