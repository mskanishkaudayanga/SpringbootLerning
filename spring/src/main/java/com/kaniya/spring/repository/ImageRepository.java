package com.kaniya.spring.repository;

import com.kaniya.spring.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
