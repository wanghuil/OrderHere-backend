package com.backend.OrderHere.controller.v1;

import com.backend.OrderHere.dto.Category.CategoryGetDto;
import com.backend.OrderHere.dto.Category.CategoryPostDto;
import com.backend.OrderHere.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryGetDto> getCategories(@PathVariable Integer restaurantId) {
        return categoryService.getCategoryByRestaurantId(restaurantId);
    }

    @PostMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryGetDto createCategory(@Valid @RequestBody CategoryPostDto categoryPostDto) {
        return categoryService.createCategory(categoryPostDto);
    }
}
