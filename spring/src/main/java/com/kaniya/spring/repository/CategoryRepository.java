package com.kaniya.spring.repository;

import com.kaniya.spring.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
     Category findByName(String name);

}
