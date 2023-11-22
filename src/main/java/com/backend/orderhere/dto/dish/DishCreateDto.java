package com.backend.orderhere.dto.dish;

import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.Pattern;
import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;

@Data
public class DishCreateDto {

    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Field must be alphanumeric")
    @Size(max = 100, message = "Field must be less than 100 characters")
    private String dishName;

    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Field must be alphanumeric")
    @Size(max = 255, message = "Field must be less than 255 characters")
    private String description;

    private BigDecimal price;

    @Pattern(regexp = "^(http(s?):\\/\\/)[^\\s]+$", message = "Field must be a valid URL")
    @Size(max = 255, message = "Field must be less than 255 characters")
    private String imageUrl;

    private Integer restaurantId;
    private Boolean availability;
}
