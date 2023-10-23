package com.backend.OrderHere.service;

import com.backend.OrderHere.dto.PagingDto;
import com.backend.OrderHere.dto.dish.DishGetDto;
import com.backend.OrderHere.mapper.DishMapper;
import com.backend.OrderHere.model.Dish;
import com.backend.OrderHere.repository.DishRepository;
import com.backend.OrderHere.service.enums.DishSort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DishService {
    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    public PagingDto<List<DishGetDto>> getDishPageByRestaurantId(Integer restaurantId,
                                                                 int page,
                                                                 int size,
                                                                 DishSort sort,
                                                                 Sort.Direction order) {
        Pageable pageable;
        if (size <= 0) {
            pageable = Pageable.unpaged();
        }
        else {
            pageable = PageRequest.of(page - 1, size, Sort.by(order, sort.getName()));
        }

        Page<Dish> dishPage = dishRepository.findDishesByRestaurantId(restaurantId, pageable);

        List<DishGetDto> dishGetDtoList = dishPage.getContent()
                .stream()
                .map(dishMapper::dishToDishGetDto)
                .toList();

        return PagingDto.<List<DishGetDto>>builder()
                .data(dishGetDtoList)
                .currentPage(dishPage.getNumber() + 1)
                .totalPages(dishPage.getTotalPages())
                .totalItems(dishPage.getTotalElements())
                .build();
    }

    public List<DishGetDto> getDishByCategory(Integer restaurantId, Integer categoryId) {
        return dishRepository.findAllByRestaurantIdAndCategoryCategoryId(restaurantId, categoryId)
                .stream()
                .map(dishMapper::dishToDishGetDto)
                .toList();
    }
}
