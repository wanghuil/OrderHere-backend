package com.backend.OrderHere.controller.v1;

import com.backend.OrderHere.dto.Restaurant.RestaurantCreateDTO;
import com.backend.OrderHere.dto.Restaurant.RestaurantGetDTO;
import com.backend.OrderHere.dto.Restaurant.RestaurantUpdateDTO;
import com.backend.OrderHere.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/public/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RestaurantGetDTO> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantGetDTO createRestaurant(@Valid @RequestBody RestaurantCreateDTO restaurantCreateDTO) {
        return restaurantService.createRestaurant(restaurantCreateDTO);
    }

    @GetMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantGetDTO getRestaurantById(@PathVariable Integer restaurantId) {
        return restaurantService.getRestaurantById(restaurantId);
    }

    @PutMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantGetDTO updateRestaurantById(@PathVariable Integer restaurantId, @Valid @RequestBody RestaurantUpdateDTO restaurantUpdateDTO) {
        return restaurantService.updateRestaurantById(restaurantId, restaurantUpdateDTO);
    }

}
