package com.kaniya.spring.dto;

import lombok.Data;

@Data
public class ImageDto {
    private long imageId;
    private String imageName;
    private  String downloadUrl;
}
