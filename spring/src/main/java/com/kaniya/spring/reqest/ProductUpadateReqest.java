package com.kaniya.spring.reqest;

import com.kaniya.spring.model.Category;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductUpadateReqest {
    private long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
