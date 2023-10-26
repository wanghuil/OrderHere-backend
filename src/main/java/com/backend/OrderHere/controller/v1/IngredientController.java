package com.backend.OrderHere.controller.v1;

import com.backend.OrderHere.model.Ingredient;
import com.backend.OrderHere.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    @GetMapping
    public List<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @GetMapping("/{id}")
    public Ingredient getIngredientById(@PathVariable Integer id) {
        return ingredientService.getIngredientById(id);
    }

    @PostMapping
    public Ingredient addIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.addIngredient(ingredient);
    }

    @PutMapping("/{id}")
    public Ingredient updateIngredient(@PathVariable Ingredient ingredient) {
        return ingredientService.updateIngredient(ingredient);
    }

    @DeleteMapping("/{id}")
    public void deleteIngredient(@PathVariable Integer id) {
        ingredientService.deleteIngredient(id);
    }
}
