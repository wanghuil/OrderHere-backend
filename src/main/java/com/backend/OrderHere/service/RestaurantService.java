package com.backend.OrderHere.service;

import com.backend.OrderHere.dto.Restaurant.RestaurantCreateDTO;
import com.backend.OrderHere.dto.Restaurant.RestaurantGetDTO;
import com.backend.OrderHere.dto.Restaurant.RestaurantUpdateDTO;
import com.backend.OrderHere.exception.DataIntegrityViolationException;
import com.backend.OrderHere.exception.ResourceNotFoundException;
import com.backend.OrderHere.mapper.RestaurantMapper;
import com.backend.OrderHere.model.Restaurant;
import com.backend.OrderHere.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    public final RestaurantRepository restaurantRepository;

    public final RestaurantMapper restaurantMapper;

    @Transactional
    public RestaurantGetDTO createRestaurant(RestaurantCreateDTO restaurantCreateDTO) {

        try {
            Restaurant restaurant = restaurantRepository.save(restaurantMapper.fromRestaurantCreateDTOToRestaurant(restaurantCreateDTO));
            return restaurantMapper.fromRestaurantToRestaurantGetDTO(restaurant);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }
    }

    public RestaurantGetDTO getRestaurantById(Integer id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
        return restaurantMapper.fromRestaurantToRestaurantGetDTO(restaurant);
    }

    public List<RestaurantGetDTO> getAllRestaurants() {
        return restaurantRepository.findAll().stream().map(restaurantMapper::fromRestaurantToRestaurantGetDTO).collect(Collectors.toList());
    }

    @Transactional
    public RestaurantGetDTO updateRestaurantById(Integer id, RestaurantUpdateDTO restaurantUpdateDTO) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
        try {
            restaurantMapper.updateRestaurantFromDto(restaurantUpdateDTO, restaurant);
            Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
            return restaurantMapper.fromRestaurantToRestaurantGetDTO(updatedRestaurant);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }
    }
}
