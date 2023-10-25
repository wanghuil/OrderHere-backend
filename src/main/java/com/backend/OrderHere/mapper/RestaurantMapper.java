package com.backend.OrderHere.mapper;

import com.backend.OrderHere.dto.Restaurant.RestaurantCreateDTO;
import com.backend.OrderHere.dto.Restaurant.RestaurantGetDTO;
import com.backend.OrderHere.dto.Restaurant.RestaurantUpdateDTO;
import com.backend.OrderHere.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {

    RestaurantGetDTO fromRestaurantToRestaurantGetDTO(Restaurant restaurant);

    Restaurant fromRestaurantCreateDTOToRestaurant(RestaurantCreateDTO restaurantCreateDTO);

    void updateRestaurantFromDto(RestaurantUpdateDTO dto, @MappingTarget Restaurant restaurant);
}
