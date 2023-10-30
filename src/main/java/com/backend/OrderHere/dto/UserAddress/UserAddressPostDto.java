package com.backend.OrderHere.dto.UserAddress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressPostDto {
    private Integer userId;
    private String address;
    private Boolean isDefault;
}
