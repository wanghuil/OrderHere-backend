package com.backend.OrderHere.repository;

import com.backend.OrderHere.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByCategoryId(Integer categoryId);
    List<Category> findAllByRestaurantId(Integer restaurantId);
    boolean existsByCategoryName(String categoryName);
}
