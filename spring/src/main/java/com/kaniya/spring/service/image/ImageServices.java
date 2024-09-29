package com.kaniya.spring.service.image;

import com.kaniya.spring.dto.ImageDto;
import com.kaniya.spring.exception.ImageNotFoundExeptipon;
import com.kaniya.spring.model.Image;
import com.kaniya.spring.model.Product;
import com.kaniya.spring.repository.ImageRepository;
import com.kaniya.spring.service.product.IProductServices;
import com.kaniya.spring.service.product.ProductServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ImageServices implements IImageServices {
    private final ImageRepository imageRepository;
    private final IProductServices productServices;
    @Override
    public Image getImageById(long id) {
        return imageRepository.findById(id)
                .orElseThrow(()->{throw new ImageNotFoundExeptipon("image Not Found");});
    }

    @Override
    public void deleteImageById(long id) {
        imageRepository.findById(id)
                .ifPresentOrElse(imageRepository::delete,()->{throw new ImageNotFoundExeptipon("image Not Found");});


    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, long product_id) {
        Product product = productServices.getProductById(product_id);
        List<ImageDto> saveImageDto =new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildUrl ="api/v1/images/image/download";
                String downloardUrl =buildUrl+image.getId();
                image.setDownloardUrl(downloardUrl);
                imageRepository.save(image);
                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(image.getId());
                imageDto.setImageName(image.getFileName());
                imageDto.setDownloadUrl(image.getDownloardUrl());
                saveImageDto.add(imageDto);
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }

        }
        return saveImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, long Image_id) {
        Image image = getImageById(Image_id);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
