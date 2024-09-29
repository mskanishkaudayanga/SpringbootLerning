package com.kaniya.spring.service.category;

import com.kaniya.spring.exception.AlredyExistsException;
import com.kaniya.spring.exception.ResouceNotFoundException;
import com.kaniya.spring.model.Category;
import com.kaniya.spring.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResouceNotFoundException("Category not Found"));
    }


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c->!categoryRepository.existsById(c.getId()))
                .orElseThrow(()->{throw new AlredyExistsException("Already Exists");});
    }

    @Override
    public Category updateCategory(Category category , long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory->{
            oldCategory.setName(category.getName());
            return categoryRepository.save(oldCategory);
        })
                .orElseThrow(() -> new ResouceNotFoundException("Category not Found"));
    }

    @Override
    public void deleteCategory(long id) {
         categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete,
                        ()->{throw new ResouceNotFoundException("Category not found");
                });

    }
}
