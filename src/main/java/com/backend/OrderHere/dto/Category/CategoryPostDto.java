package com.backend.OrderHere.dto.Category;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryPostDto {

    @NotNull(message = "Restaurant id must be provided.")
    private Integer restaurantId;

    @NotNull(message = "Category name must be provided.")
    private String categoryName;
}
