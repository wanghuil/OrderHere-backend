package com.backend.OrderHere.repository;

import com.backend.OrderHere.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {


}
