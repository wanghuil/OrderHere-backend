package com.backend.OrderHere.mapper;

import com.backend.OrderHere.dto.Category.*;
import com.backend.OrderHere.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    CategoryGetDto CategoryToCategoryGetDto(Category category);
    Category CategoryPostDtoToCategory(CategoryPostDto categoryPostDto);
}
