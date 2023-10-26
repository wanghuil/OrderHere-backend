package com.backend.OrderHere.service;

import com.backend.OrderHere.dto.Rating.RatingGetDto;
import com.backend.OrderHere.dto.Rating.RatingPostDto;
import com.backend.OrderHere.mapper.RatingMapper;
import com.backend.OrderHere.model.Dish;
import com.backend.OrderHere.model.Rating;
import com.backend.OrderHere.repository.DishRepository;
import com.backend.OrderHere.repository.RatingRepository;
import com.backend.OrderHere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RatingService {
    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final DishRepository dishRepository;
    private final UserRepository userRepository;

    public List<RatingGetDto> getRatingsByDishId(Integer dishId) {
        List<Rating> ratingList = ratingRepository.findAllByDishDishId(dishId);
        return ratingList.stream()
                .map(ratingMapper::ratingToRatingGetDto)
                .toList();
    }

    public List<RatingGetDto> getRatingsByUserId(Integer userId) {
        List<Rating> ratingList = ratingRepository.findAllByUserUserId(userId);
        return ratingList.stream().map(ratingMapper::ratingToRatingGetDto).toList();
    }

    public RatingGetDto createRating(RatingPostDto ratingPostDto) {
        Rating rating = ratingMapper.ratingPostDtoToRating(ratingPostDto);
        rating.setDish(dishRepository.findByDishId(ratingPostDto.getDishId()));
        rating.setUser(userRepository.findByUserId(ratingPostDto.getUserId()));
        ratingRepository.save(rating);
        updateDishRating(rating.getDish().getDishId());

        return ratingMapper.ratingToRatingGetDto(rating);
    }

    public void deleteRating(Integer ratingId) {
        ratingRepository.deleteByRatingId(ratingId);
        updateDishRating(ratingRepository.findByRatingId(ratingId).getDish().getDishId());
    }

    void updateDishRating(Integer dishId) {
        Long ratingCount = ratingRepository.countByDishId(dishId);
        BigDecimal averageRating = ratingRepository.calculateAverageRatingForDish(dishId);
        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new IllegalArgumentException("Dish not found"));
        if (averageRating == null || ratingCount<5) {
            dish.setRating(null);
            log.info("Dish : {} doesn't have enough ratings yet", dishId);
        } else {
            dish.setRating(averageRating);
            log.info("Dish : {} current rating changes to {}", dishId, averageRating);
        }
        dishRepository.save(dish);
    }
}
