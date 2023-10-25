package com.backend.OrderHere.dto.Restaurant;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RestaurantGetDTO {

    private Integer restaurantId;
    private String name;
    private String description;
    private String address;
    private String contactNumber;
    private String abn;
    private String ownerName;
    private String ownerMobile;
    private String ownerAddress;
    private String ownerEmail;
    private String ownerCrn;
    private BigDecimal averageRating;

}
