package com.backend.orderhere.controller.v1;

import com.backend.orderhere.dto.ingredient.DeleteIngredientDTO;
import com.backend.orderhere.dto.ingredient.GetIngredientDTO;
import com.backend.orderhere.dto.ingredient.PostIngredientDTO;
import com.backend.orderhere.model.Ingredient;
import com.backend.orderhere.model.LinkIngredientDish;
import com.backend.orderhere.service.IngredientService;
import com.backend.orderhere.service.LinkIngredientDishService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/public/ingredients")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private LinkIngredientDishService linkIngredientDishService;

    @GetMapping
    public List<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @PostMapping
    public LinkIngredientDish createLink(@RequestBody PostIngredientDTO postIngredientDTO) {
        return linkIngredientDishService.createLink(postIngredientDTO);
    }

    // @GetMapping("dish/{dishID}")
    // public ResponseEntity<List<GetIngredientDTO>> findByDishID(@PathVariable Integer dishID) {
    //     List<GetIngredientDTO> dtoList = linkIngredientDishService.findGetIngredientDTOByDishID(dishID);
    //     return ResponseEntity.ok(dtoList);
    // }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteLink(@RequestBody DeleteIngredientDTO deleteIngredientDTO) {
        try {
            linkIngredientDishService.deleteById(deleteIngredientDTO);
            return ResponseEntity.ok("Link deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
