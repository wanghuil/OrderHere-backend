package com.backend.OrderHere.service;

import com.backend.OrderHere.dto.Category.CategoryGetDto;
import com.backend.OrderHere.dto.Category.CategoryPostDto;
import com.backend.OrderHere.mapper.CategoryMapper;
import com.backend.OrderHere.model.Category;
import com.backend.OrderHere.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryGetDto> getCategoryByRestaurantId(Integer restaurantId) {
      List<CategoryGetDto> categoryList = categoryRepository.findAllByRestaurantId(restaurantId).stream().map(categoryMapper::CategoryToCategoryGetDto).toList();
      return categoryList;
    }

    @Transactional
    public CategoryGetDto createCategory(CategoryPostDto categoryPostDto) {
        isExist(categoryPostDto.getCategoryName());

        Category category = categoryMapper.CategoryPostDtoToCategory(categoryPostDto);
        return categoryMapper.CategoryToCategoryGetDto(categoryRepository.save(category));
    }

    public void isExist(String name) {

        boolean isExist = categoryRepository.existsByCategoryName(name);
        if (isExist) {
            throw new RuntimeException("Game already existed!");
        }
    }
}
