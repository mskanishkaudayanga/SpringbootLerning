package com.kaniya.spring.controller;

import com.kaniya.spring.dto.ImageDto;
import com.kaniya.spring.exception.ResouceNotFoundException;
import com.kaniya.spring.model.Image;
import com.kaniya.spring.response.ApiResponse;
import com.kaniya.spring.service.image.IImageServices;
import com.kaniya.spring.service.image.ImageServices;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@AllArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageServices imageServices;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> file , @RequestParam long productId) {
        try{
            List<ImageDto> imageDtos = imageServices.saveImages(file, productId);
            return ResponseEntity.ok(new ApiResponse("Upload Succesfull!",imageDtos));
        } catch (Exception e) {
           return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload Failed!",e.getMessage()));
        }
    }
    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<ByteArrayResource> downloadImages(@PathVariable long imageId) throws SQLException {
        try {
            Image image = imageServices.getImageById(imageId);
            // Convert the image data into a ByteArrayResource
            ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(image.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                    .body(resource);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable long imageId, @RequestBody MultipartFile file){
        try {
            Image image = imageServices.getImageById(imageId);
            if(image != null){
                imageServices.updateImage(file, imageId);
                return  ResponseEntity.ok().body(new ApiResponse("Update Succesfull!",null));
            }
        } catch (ResouceNotFoundException e) {
          return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update Failed!",INTERNAL_SERVER_ERROR));

    }


    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable long imageId){
        try {
            Image image = imageServices.getImageById(imageId);
            if(image != null){
                imageServices.deleteImageById( imageId);
                return  ResponseEntity.ok().body(new ApiResponse("Delete Succesfull!",null));
            }
        } catch (ResouceNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("delete Failed!",INTERNAL_SERVER_ERROR));

    }


}
