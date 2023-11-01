package com.backend.orderhere.service;

import com.backend.orderhere.dto.PagingDto;
import com.backend.orderhere.dto.dish.DishGetDto;
import com.backend.orderhere.mapper.DishMapper;
import com.backend.orderhere.model.Dish;
import com.backend.orderhere.repository.DishRepository;
import com.backend.orderhere.service.enums.DishSort;
import com.backend.orderhere.util.PageableUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    Pageable pageable = PageableUtil.determinePageable(page, size, Sort.by(order, sort.getName()));

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
