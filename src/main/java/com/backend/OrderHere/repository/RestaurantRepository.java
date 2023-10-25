package com.backend.OrderHere.repository;

import com.backend.OrderHere.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
}
