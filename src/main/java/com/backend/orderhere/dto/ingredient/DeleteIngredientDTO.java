package com.backend.orderhere.dto.ingredient;

import lombok.Data;

@Data
public class DeleteIngredientDTO {
    private Integer dishID;
    private Integer ingredientID;
}
