package com.backend.OrderHere.service;

import com.backend.OrderHere.model.Ingredient;
import com.backend.OrderHere.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    public Ingredient addIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredientById(Integer id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    public Ingredient updateIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public void deleteIngredient(Integer id) {
        ingredientRepository.deleteById(id);
    }
}
