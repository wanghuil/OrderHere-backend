package com.backend.OrderHere.mapper;

import com.backend.OrderHere.dto.UserAddress.*;
import com.backend.OrderHere.model.UserAddress;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserAddressMapper {
    UserAddressGetDto UserAddressToUserAddressGetDto(UserAddress userAddress);
    UserAddress UserAddressPostDtoToUserAddress(UserAddressPostDto userAddressPostDto);
    UserAddress UserAddressPutDtoToUserAddress(UserAddressPutDto userAddressPutDto);
}
