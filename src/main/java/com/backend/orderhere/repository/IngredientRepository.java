package com.backend.orderhere.repository;

import com.backend.orderhere.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {


}
