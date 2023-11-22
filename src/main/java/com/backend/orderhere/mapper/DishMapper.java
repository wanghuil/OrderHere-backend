package com.backend.orderhere.mapper;

import com.backend.orderhere.dto.dish.DishCreateDto;
import com.backend.orderhere.dto.dish.DishGetDto;
import com.backend.orderhere.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DishMapper {
  @Mapping(source = "category.categoryId", target = "categoryId")
  @Mapping(source = "category.categoryName", target = "categoryName")
  DishGetDto dishToDishGetDto(Dish dish);

  Dish dishCreateDtoToDish(DishCreateDto dishCreateDto);
}
