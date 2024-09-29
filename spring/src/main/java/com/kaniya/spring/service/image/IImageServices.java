package com.kaniya.spring.service.image;

import com.kaniya.spring.dto.ImageDto;
import com.kaniya.spring.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageServices {
    Image getImageById(long id);
    void deleteImageById(long id);
    List<ImageDto> saveImages(List<MultipartFile> files, long product_id);
    void updateImage(MultipartFile file, long Image_id);
}
