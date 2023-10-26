package com.backend.OrderHere.mapper;

import com.backend.OrderHere.dto.Rating.RatingGetDto;
import com.backend.OrderHere.dto.Rating.RatingPostDto;
import com.backend.OrderHere.model.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RatingMapper {
    Rating ratingPostDtoToRating(RatingPostDto ratingPostDto);

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "dish.dishId", target = "dishId")
    RatingGetDto ratingToRatingGetDto(Rating rating);

}
